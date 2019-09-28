create table comment
(
	id BIGINT auto_increment,
	parent_id BIGINT not null,
	type int not null comment '父类类型',
	commentator int null comment '评论人id
',
	gmt_create bigint not null comment '创建时间
',
	gmt_modified bigint not null comment '更新时间',
	like_count BIGINT default 0 null,
	constraint comment_pk
		primary key (id)
);