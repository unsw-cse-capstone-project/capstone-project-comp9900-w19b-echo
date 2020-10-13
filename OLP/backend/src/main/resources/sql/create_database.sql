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
    house_number varchar(30) not null,
    longitude double not null,
    latitude double not null,
    status int default 0,
    register_time datetime not null,
    verification int default 0
)
auto_increment=100000;