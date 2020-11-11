use onpro;

create table t_user(
    uid int auto_increment primary key,
    username varchar(30) not null,
    password varchar(100) not null,
    role varchar(10) not null,
    email varchar(100) not null,
    phone varchar(20),
    nickname varchar(15) default 'new user',
    register_time datetime not null,
    verification int default 0
)
auto_increment=1000000;

create table t_property(
    pid int auto_increment primary key,
    owner int not null,
    state varchar(30) not null,
    city varchar(30) not null,
    district varchar(30) not null,
    code varchar(30) not null,
    street varchar(50) not null,
    house_number varchar(30) not null,
    longitude double not null,
    latitude double not null,
    address varchar(200) not null,
    status int default 0,
    create_time datetime not null,
    area double not null,
    bedroom int default 0,
    bathroom int default 0,
    carport int default 0,
    property_type int default 0,
    verification int default 0,
    description varchar(5000),
    remark varchar(5000)
)
auto_increment=100000;
create index tpo_index on t_property(owner);

create table t_auction(
    aid int auto_increment primary key,
    uid int not null,
    pid int not null,
    base_price double not null,
    current_price double default 0,
    begin_time datetime not null,
    status int default 0,
    end_time datetime not null,
    winner int
)
auto_increment=1000000;
create index tap_index on t_auction(pid);

create table user_message(
    uid int not null,
    pid int,
    aid int,
    sender varchar(30) not null,
    send_time datetime,
    subject varchar(50) not null,
    content varchar(300) not null,
    read_flag int default 0,
    serial int auto_increment primary key
);
create index um_index on user_message(uid);

create table user_favorite(
    uid int not null,
    pid int not null,
    add_time datetime,
    serial int auto_increment primary key
);
create index uf_index on user_favorite(uid);

create table user_view_history(
    uid int not null,
    pid int not null,
    serial int auto_increment primary key
);
create index uvh_index on user_view_history(uid);

create table user_search_history(
    uid int not null,
    state varchar(30) not null,
    city varchar(30) not null,
    district varchar(30) not null,
    code varchar(30) not null,
    house_number varchar(30) not null,
    longitude double not null,
    latitude double not null,
    search_time datetime not null,
    area double not null,
    bedroom int default 0,
    bathroom int default 0,
    carport int default 0,
    property_type int default 0,
    serial int auto_increment primary key
);
create index ush_index on user_search_history(uid);

create table auction_register(
    aid int not null,
    uid int not null,
    pid int not null,
    user_type int not null,
    base_price double,
    serial int auto_increment  primary key
);
create index ar_index on auction_register(aid);

create table auction_bid(
    aid int not null,
    uid int not null,
    pid int not null,
    bid_time datetime not null,
    bid_value double not null,
    serial int auto_increment primary key
);
create index ab_index on auction_bid(aid);

create table user_payment(
    uid int not null,
    name_on_card varchar(30) not null,
    card_number varchar(30) not null,
    expire_month int not null,
    expire_year int not null,
    cvv int not null,
    add_time datetime not null,
    street_address varchar(100) not null,
    suburb varchar(30) not null,
    postcode varchar(15) not null,
    state varchar(20) not null,
    country varchar(30) not null,
    serial int auto_increment primary key
);
create index up_index on user_payment(uid);

create table user_habit(
    uid int not null,
    term varchar(20) not null,
    term_hot int default 1,
    primary key (uid, term)
);
