--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'DEMANDCENTER_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('DEMANDCENTER_MANAGEMENT','demandcenter.adminFeature.ManageDemandcenter.name',1,'jsp/admin/plugins/demandcenter/ManageAttributes.jsp','demandcenter.adminFeature.ManageDemandcenter.description',0,'demandcenter',NULL,NULL,NULL,4);

DELETE FROM core_admin_right WHERE id_right = 'DEMANDS_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('DEMANDS_MANAGEMENT','demandcenter.adminFeature.ManageDemands.name',1,'jsp/admin/plugins/demandcenter/ManageDemands.jsp','demandcenter.adminFeature.ManageDemands.description',0,'demandcenter',NULL,NULL,NULL,5);

DELETE FROM core_admin_right WHERE id_right = 'USER_PREFERENCES_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('USER_PREFERENCES_MANAGEMENT','demandcenter.adminFeature.ManageUserPreferences.name',1,'jsp/admin/plugins/demandcenter/UserPreferences.jsp','adminFeature.ManageUserPreferences.description',0,'demandcenter',NULL,NULL,NULL,6);

--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'DEMANDCENTER_MANAGEMENT';
DELETE FROM core_user_right WHERE id_right = 'DEMANDS_MANAGEMENT';
DELETE FROM core_user_right WHERE id_right = 'USER_PREFERENCES_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('DEMANDCENTER_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('DEMANDS_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('USER_PREFERENCES_MANAGEMENT',1);

