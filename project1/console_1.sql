create table Post(
    postID int not null primary key,
    title varchar(100),
    content varchar(1000)
);

create table Post_Detail(
    postID int not null primary key,
    postTime varchar(50),
    postCity varchar(50),
    postCountry varchar(50),
    foreign key (postID) references Post(postID)
);

create table Favorite_Post(
    favoriteID  serial PRIMARY KEY,
    postID int not null ,
    favorite varchar(30),
    unique(postID,favorite),
    foreign key (postID) references Post(postID)
);
create table Share_Post(
    shareID  serial PRIMARY KEY,
    postID int not null,
    share varchar(30),
    unique(postID,share),
    foreign key (postID) references Post(postID)
);
create table Like_Post(
    likeID  serial PRIMARY KEY,
    postID int not null unique,
    liked varchar(30),
     unique(postID,liked),
    foreign key (postID) references Post(postID)
);
create table Categories(
    categoryID  serial PRIMARY KEY,
    postID int not null unique ,
    category varchar(30),
     unique(postID,category),
    foreign key (postID) references Post(postID)
);
create table Author(
     postID int not null primary key,
     authorName varchar(30),
     registrationTime varchar(30),
     authorID varchar(30) unique,
     phone varchar(30),
     foreign key (postID) references Post(postID)
);
create table Followed_By(
    followID  serial PRIMARY KEY,
    authorID varchar(30) not null,
    follow varchar(30),
    unique(authorID,follow),
    foreign key (authorID) references Author(authorID)
);
create table First_Reply(
    firstID  serial PRIMARY KEY,
    postID int not null ,
    firstContent varchar(500),
    firstStars int,
    firstAuthor varchar(30),
    unique (postID,firstAuthor),
    foreign key (postID) references Post(postID)
);
create table Second_Reply(
    secondID  serial PRIMARY KEY,
    postID int not null,
    firstAuthor varchar(30),
    secondaryContent varchar(500),
    secondaryStars int,
    secondaryAuthor varchar(30),
    --unique (postID,firstAuthor,secondaryAuthor), duplicate
    unique (postID,secondaryStars,secondaryAuthor),
    --unique (postID,firstAuthor,secondaryStars,secondaryAuthor),
    foreign key (postID,firstAuthor) references First_Reply(postID,firstAuthor)
);

select count(*) from post;

select count(*) from like_post
where postID = 163;

select count(*)
from like_post
where postID = 28;

select count(*)
from like_post
where postID = 6;

select count(*)
from favorite_post
where postID = 163;

select count(*)
from favorite_post
where postID = 13;

select count(*)
from favorite_post
where postID = 6;

select count(*)
from share_post
where postID = 163;

select count(*)
from share_post
where postID = 28;

select count(*)
from share_post
where postID = 6;

with x as (select count(favorite)cnt,postID
from favorite_post
group by postID)
select postID,cnt
from x where cnt = (select max(cnt) from x);

select count(follow),authorName
from followed_by
join author a on Followed_By.authorID = a.authorid
group by a.authorID, authorName;

select count(Followed_By.authorID),follow
from followed_by
join author a on Followed_By.authorID = a.authorid
group by Followed_By.follow;

select postID from post_detail
where postTime = (select min(postTime) from post_detail);

select count(*) from post_detail
where postTime like '2020%';