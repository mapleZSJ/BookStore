create database Onlinebooksales

create table userinfo(
uno int identity(1,1) primary key,
uname nvarchar(20) not nuLL,
pwd char(15),
sex char(2) default '男' check(sex='男' OR sex='女'),
cardnum nvarchar(20) unique,
cpwd char(6),
authority bit default 0,
birthday datetime check(birthday<=getdate()));


create table bkind(
kno nvarchar(5) primary key,
kind nvarchar(10));

create table binfo(
bno nvarchar(13) primary key ,
bname nvarchar(20) not nuLL,
author nvarchar(20) ,
introduction nvarchar(80) ,
price money check(price>=0),
stock int check(stock>=0),
bookstate bit default 0,
kno nvarchar(5),
foreign key (kno) references bkind(kno));

create table ainfo(
ano int identity(1,1) primary key,
name nvarchar(20) ,
addr nvarchar(30),
postcode char(6),
phone varchar(11) not null,
defaultstate bit default 0,
uno int,
foreign key (uno) references userinfo(uno));

create table orderinfo(
ono int identity(1,1) primary key,
amount int default '1' check(amount>=1) ,
bmoney money check(bmoney>=0),
texpenses money default '10.00' check(texpenses>=0),
paymentstate bit default 0,
ctime datetime default getdate(),
uno int,
ano int,
foreign key (uno) references userinfo(uno),
foreign key (ano) references ainfo(ano));

create table orderdetail(
dno int identity(1,1) primary key,
bamount int default '1' check(bamount>=1),
bprice money check(bprice>=0),
ono int,
bno nvarchar(10),
foreign key (ono) references orderinfo(ono),
foreign key (bno) references binfo(bno));

create table shoppingcart(
cno int identity(1,1) primary key,
price money check(price>=0),
amount int default '1' check(amount>=1),
uno int,
bno nvarchar(10),
foreign key (uno) references userinfo(uno),
foreign key (bno) references binfo(bno));



//备注：

create database 网上图书销售系统

create table 用户信息表(
用户ID int identity(1,1) primary key,
用户名 nvarchar(20) not nuLL,
密码 char(15),
性别 char(2) default '男' check(性别='男' OR 性别='女'),
银行卡号 nvarchar(20) unique,
支付密码 char(6),
权限标识 bit default 0,
出生日期 datetime check(出生日期<=getdate()));


create table 图书种类表(
种类ID nvarchar(5) primary key,
类型 nvarchar(10));

create table 图书信息表(
图书ID nvarchar(13) primary key ,
书名 nvarchar(20) not nuLL,
作者 nvarchar(20) ,
简介 nvarchar(80) ,
价格 money check(价格>=0),
库存量 int check(库存量>=0),
是否下架 bit default 0,
种类ID nvarchar(5),
foreign key (种类ID) references 图书种类表(种类ID));

create table 地址表(
地址ID int identity(1,1) primary key,
收件人 nvarchar(20) ,
收件地址 nvarchar(30),
邮编 char(6),
联系电话 varchar(11) not null,
是否默认 bit default 0,
用户ID int,
foreign key (用户ID) references 用户信息表(用户ID));

create table 订单记录表(
订单ID int identity(1,1) primary key,
总数目 int default '1' check(总数目>=1) ,
总金额 money check(总金额>=0),
运费 money default '10.00' check(运费>=0),
支付状态 bit default 0,
创建时间 datetime default getdate(),
用户ID int,
地址ID int,
foreign key (用户ID) references 用户信息表(用户ID),
foreign key (地址ID) references 地址表(地址ID));

create table 订单明细表(
明细ID int identity(1,1) primary key,
图书数量 int default '1' check(图书数量>=1),
图书价格 money check(图书价格>=0),
订单ID int,
图书ID nvarchar(10),
foreign key (订单ID) references 订单记录表(订单ID),
foreign key (图书ID) references 图书信息表(图书ID));

create table 购物车记录表(
购物车ID int identity(1,1) primary key,
单价 money check(单价>=0),
总数目 int default '1' check(总数目>=1),
用户ID int,
图书ID nvarchar(10),
foreign key (用户ID) references 用户信息表(用户ID),
foreign key (图书ID) references 图书信息表(图书ID));



存储过程：
（1）create proc user_order
@uname as nvarchar(10),@pwd as char(15)
as
begin

select ono,amount,bmoney,texpenses,paymentstate,ctime,addr
from orderinfo left join ainfo on orderinfo.ano=ainfo.ano,userinfo 
where orderinfo.uno=userinfo.uno 
and uname=@uname and pwd=@pwd
order by ono desc

end

（2）create proc user_cart
@uname as nvarchar(10),@pwd as char(15)
as
begin

select bname,shoppingcart.price,amount
from shoppingcart,binfo,userinfo 
where shoppingcart.bno=binfo.bno and shoppingcart.uno=userinfo.uno 
and uname=@uname and pwd=@pwd
order by cno desc

end

（3）create proc user_address
@uname as nvarchar(10),@pwd as char(15)
as
begin

select name,addr,postcode,phone,defaultstate
from ainfo
where uno=(select uno from userinfo where uname=@uname and pwd=@pwd)

end



