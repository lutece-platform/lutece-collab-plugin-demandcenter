
--
-- Structure for table demandcenter_attribute
--

DROP TABLE IF EXISTS demandcenter_attribute;
CREATE TABLE demandcenter_attribute (
id_attribute int(6) NOT NULL,
code varchar(255) default '' NOT NULL,
label varchar(255) default '' NOT NULL,
mandatory SMALLINT NOT NULL,
may_be_filled_by_identity_service SMALLINT NOT NULL,
PRIMARY KEY (id_attribute)
);

--
-- Structure for table demandcenter_channel
--

DROP TABLE IF EXISTS demandcenter_channel;
CREATE TABLE demandcenter_channel (
id_channel int(6) NOT NULL,
label varchar(255) default '' NOT NULL,
icon_font varchar(50) default '',
code varchar(255) default '' NOT NULL,
PRIMARY KEY (id_channel)
);

--
-- Structure for table demandcenter_contactmode
--

DROP TABLE IF EXISTS demandcenter_contactmode;
CREATE TABLE demandcenter_contactmode (
id_contact_mode int(6) NOT NULL,
code varchar(255) default '' NOT NULL,
label varchar(255) default '' NOT NULL,
icon_font varchar(50) default '',
PRIMARY KEY (id_contact_mode)
);

--
-- Structure for table demandcenter_category
--

DROP TABLE IF EXISTS demandcenter_category;
CREATE TABLE demandcenter_category (
id_category int(6) NOT NULL,
id_parent int(11) default '-1',
label varchar(255) default '' NOT NULL,
n_order int(6) default '0',
code varchar(255) default '' NOT NULL,
id_default_assignee_unit int(6) NOT NULL,
id_category_type int(6) NOT NULL,
id_workflow int(6) NOT NULL,
PRIMARY KEY (id_category)
);

--
-- Structure for table demandcenter_demand
--

DROP TABLE IF EXISTS demandcenter_demand;
CREATE TABLE demandcenter_demand (
id_demand int(6) NOT NULL,
reference varchar(50) default '' NOT NULL,
is_read SMALLINT,
comment long varchar,
date_create timestamp NOT NULL,
date_close timestamp NOT NULL,
demand_content long varchar NOT NULL,
code_form varchar(255) default '',
guid varchar(255) default '',
id_category int(11) default '0' NOT NULL,
id_assignee_user int(11) default '0',
id_assignee_unit int(11) default '0',
id_channel int(11) default '0',
id_contact_mode int(11) default '0',
date_last_update timestamp NOT NULL,
PRIMARY KEY (id_demand)
);

--
-- Structure for table demandcenter_attribute_demand
--

DROP TABLE IF EXISTS demandcenter_attribute_demand;
CREATE TABLE demandcenter_attribute_demand (
id int(6) NOT NULL,
id_demand int(11) default '0' NOT NULL,
id_attribute varchar(255) default '',
value long varchar NOT NULL,
filled_by_demand_content SMALLINT NOT NULL,
PRIMARY KEY (id)
);

--
-- Structure for table demandcenter_category_type
--

DROP TABLE IF EXISTS demandcenter_category_type;
CREATE TABLE demandcenter_category_type (
id_category_type int(6) NOT NULL,
label varchar(255) default '' NOT NULL,
depth int(11) default '0' NOT NULL,
PRIMARY KEY (id_category_type)
);
