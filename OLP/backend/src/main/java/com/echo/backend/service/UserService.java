package com.echo.backend.service;

import com.echo.backend.dao.*;
import com.echo.backend.domain.*;
import com.echo.backend.dto.ReadMessageRequest;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final PaymentDetailMapper paymentDetailMapper;

    private final UserFavoriteMapper userFavoriteMapper;

    private final PropertyMapper propertyMapper;

    private final UserMessageMapper userMessageMapper;

    private final UserHabitMapper userHabitMapper;

    private final Directory ramDirectory;

    private static Analyzer analyzer = new StandardAnalyzer();

    @Autowired
    public UserService(UserMapper userMapper, PaymentDetailMapper paymentDetailMapper, UserFavoriteMapper userFavoriteMapper, PropertyMapper propertyMapper, UserMessageMapper userMessageMapper, UserHabitMapper userHabitMapper, Directory ramDirectory) {
        this.userMapper = userMapper;
        this.paymentDetailMapper = paymentDetailMapper;
        this.userFavoriteMapper = userFavoriteMapper;
        this.propertyMapper = propertyMapper;
        this.userMessageMapper = userMessageMapper;
        this.userHabitMapper = userHabitMapper;
        this.ramDirectory = ramDirectory;
    }

    public User getUserByEmail(String email){
        List<User> retList = userMapper.selectUserByEmail(email);
        if (retList != null && retList.size()>0){
            return retList.get(0);
        }
        return null;
    }

    public User getUserByName(String userName){
        List<User> retList = userMapper.selectUserByName(userName);
        if (retList != null && retList.size()>0){
            return retList.get(0);
        }
        return null;
    }

    public void addNewUser(User user){
        userMapper.createUser(user);
    }

    public void updateUserInfo(User user){
        userMapper.updateUser(user);
    }

    public void updateUserPassword(User user){
        userMapper.updateUserPassword(user);
    }

    public void verifyUser(User user){
        userMapper.verifyUser(user);
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    public void addPayment(PaymentDetail paymentDetail) {
        paymentDetail.setAddTime(new Date());
        paymentDetailMapper.addPaymentDetail(paymentDetail);
    }

    public List<PaymentDetail> getPaymentByUid(int uid) {
        return paymentDetailMapper.getPaymentDetailByUid(uid);
    }

    public void updatePaymentBySerial(PaymentDetail paymentDetail) {
        paymentDetailMapper.updatePaymentDetail(paymentDetail);
    }

    public void deletePaymentBySerial(PaymentDetail paymentDetail) {
        paymentDetailMapper.deletePaymentDetail(paymentDetail.getSerial());
    }

    public void updateUserAddressBySerial(PaymentDetail paymentDetail) {
        paymentDetailMapper.updateUserAddress(paymentDetail);
    }

    public void addFavorite(int uid, int pid) {
        UserFavorite favorite = new UserFavorite();
        favorite.setUid(uid);
        favorite.setPid(pid);
        favorite.setAddTime(new Date());
        userFavoriteMapper.addFavorite(favorite);
    }

    public void cancelFavorite(int uid, int pid) {
        UserFavorite favorite = new UserFavorite();
        favorite.setUid(uid);
        favorite.setPid(pid);
        userFavoriteMapper.removeFavorite(favorite);
    }

    public List<Property> getMyFavorite(int uid) {

        return propertyMapper.getMyFavorite(uid);
    }

    public User getUserByUid(int uid) {
        List<User> retList = userMapper.selectUserByUid(uid);
        if (retList != null && retList.size()>0){
            return retList.get(0);
        }
        return null;
    }

    public void sendMessage(UserMessage userMessage){
        userMessageMapper.sendMessage(userMessage);
    }
    public void deleteMessage(int serial) {
        userMessageMapper.deleteMessage(serial);
    }

    public List<UserMessage> viewMyMessage(int uid) {
        return userMessageMapper.viewMyMessage(uid);
    }

    public List<Property> getRecommandProperty(int uid) throws IOException, ParseException {

        List<UserHabit> habits = userHabitMapper.listUserHabit(uid);

        if (CollectionUtils.isEmpty(habits)) {
            return null;
        }

        String terms = habits.stream().map(UserHabit::getTerm).collect(Collectors.joining(" "));
        return luceneSearch(terms);

    }

    public List<Property> luceneSearch(String terms) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        DirectoryReader reader = DirectoryReader.open(ramDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("address", analyzer);
        Query query = parser.parse(terms);
        TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        List<Property> properties = new ArrayList<>();
        for (ScoreDoc hit : hits) {
            int docId = hit.doc;
            Document d = searcher.doc(docId);
            int pid = Integer.parseInt(d.get("pid"));
            properties.add(propertyMapper.getPropertyByPid(pid).get(0));
        }
        // reader can only be closed when there
        // is no need to access the documents any more.
        reader.close();
        return properties;
    }

    @Async("taskExecutor")
    public void collectHabitFromFavorite(int uid, int pid) {
        Property property = propertyMapper.getPropertyByPid(pid).get(0);
        linkPropertyToHabit(uid, property.getAddress(), 5);
    }

    @Async("taskExecutor")
    public void collectHabitFromViewProperty(int uid, int pid) {
        Property property = propertyMapper.getPropertyByPid(pid).get(0);
        linkPropertyToHabit(uid, property.getAddress(), 1);
    }

    @Async("taskExecutor")
    public void collectHabitFromSearchPropertyKeyword(int uid, String keyword) {
        linkPropertyToHabit(uid, keyword, 3);
    }

    @Async("taskExecutor")
    public void collectHabitFromSearchPropertyAddress(int uid, Property property) {
        String address = property.getSuburb() + " " + property.getCity() + " " + property.getState()
                + " " + property.getPostcode() + " " + property.getStreetName();
        linkPropertyToHabit(uid, address, 2);
    }

    @Async("taskExecutor")
    public void collectHabitFromRegisterAuction(int uid, int pid) {
        Property property = propertyMapper.getPropertyByPid(pid).get(0);
        linkPropertyToHabit(uid, property.getAddress(), 10);
    }

    private void linkPropertyToHabit(int uid, String address, int hot) {
        try {
            TokenStream ts = analyzer.tokenStream("address", address);
            ts.reset();
            CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
            List<UserHabit> terms = new ArrayList<>();
            while (ts.incrementToken()) {
                String str = cta.toString();
                if (str.matches("[0-9]+")) {
                    if (str.length()>3){
                        UserHabit habit = new UserHabit();
                        habit.setUid(uid);
                        habit.setTerm(str);
                        habit.setTermHot(hot);
                        terms.add(habit);
                    }
                }
                else {
                    if (!(str.equals("st") || str.equals("block") || str.equals("ave")
                || str.equals("rd") || str.equals("pl"))){
                        UserHabit habit = new UserHabit();
                        habit.setUid(uid);
                        habit.setTerm(str);
                        habit.setTermHot(hot);
                        terms.add(habit);
                    }
                }
            }
            ts.end();
            ts.close();
            terms.forEach(userHabitMapper::updateUserHabit);
        }
        catch (Exception ignored){}
    }

    public void readMessage(ReadMessageRequest request) {
        userMessageMapper.readMessage(request);
    }
}
