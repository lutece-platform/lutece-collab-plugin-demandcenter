INSERT INTO demandcenter_attribute (id_attribute, code, label, mandatory, may_be_filled_by_identity_service) VALUES 
(1, 'first_name', 'Prénom', 1, 1 ),
(2, 'last_name', 'Nom',1,1),
(3,'email','Email',1,1),
(4,'gender','Civilité',0,2);


INSERT INTO demandcenter_channel VALUES 
(1,'Téléphone','fa fa-phone','phone');

INSERT INTO demandcenter_contactmode VALUES 
(1,'phone','Téléphone','fa fa-phone');

INSERT INTO demandcenter_category_type VALUES 
(3,'Problématique',2),
(1,'Direction',0),
(2,'Nature',1);

INSERT INTO demandcenter_category VALUES 
(1,-1,'DRH',0,'DIRDRH',1,1,0),
(2,1,'Demande remboursement',0,'NATDEMREMB',1,2,0),
(6,2,'Remboursement transport',0,'PRBREMBTRANSP',1,3,0),
(9,1,'Demande carte cantine',0,'NATDEMCARTE',1,2,0),
(10,-1,'DSTI',0,'DIRDSTI',-1,1,0);

INSERT INTO demandcenter_attribute_demand (id, id_demand, id_attribute, value, filled_by_demand_content) VALUES 
(1, 7, 1, 'John', 1 ),
(2, 7, 2, 'Doe',1 ),
(3,7,3,'john.doe@paris.fr',1);


INSERT INTO demandcenter_demand VALUES 
(7,'',0,'Cette demande concerne le remboursement de la carte de transport Navigo','2017-08-01 14:55:30','2017-07-28 14:00:19','{  \"form_submit\": {   \"code_form\": \"sollicitation\",    \"comment\": \"Bonjour, ...\",    \"channel\": \"Test\",    \"contact_mode\": \"Test\",    \"category\": \"test6\",    \"username\":\"ah12h-1294ab\",    \"entries\": {      \"list\": [         {          \"question\": {            \"identity_attr_code\" : \"family_name\",            \"code\" : \"NAME\",            \"label\": \"Nom de famille\",            \"type\": \"TEXT\"          },          \"answers\": {            \"list\": [              {                \"code\" : \"MUSEUM\", \"value\": \"MusÃ©e general d\'apprentissage\"              }            ]          }        },        {          \"question\": {            \"code\": \"REVENUN2\",            \"label\": \"Revenu fiscal de reference\",            \"type\": \"NUMERIC\"          },          \"answers\": {            \"list\": [              {                \"value\": \"20000\"              }            ]          },          \"entries\": {            \"list\": [              {                \"question\": {                  \"code\" : \"RFR_DISCOUNT\",                  \"label\": \"dont reductions d\'impots\",                  \"type\": \"NUMERIC\"                },                \"answers\": {                  \"list\": [                    {                      \"value\": \"5000\"                    }                  ]                }              }            ]          }        }      ]    }  }}','sollicitation','ah12h-1294ab',6,-1,1,1,1,'2017-07-28 14:00:19');

INSERT INTO core_admin_role VALUES
('drh','Role direction des ressources humaines');

INSERT INTO core_user_role VALUES
('drh',1);

INSERT INTO core_admin_role_resource VALUES
('1000','drh','CATEGORY_DEMAND','1','VIEW_DEMAND');
