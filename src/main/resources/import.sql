INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO USER (ID, EMAIL, PASSWORD, FIRSTNAME, LASTNAME, USERNAME) VALUES (1, 'ggffggg0000@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','Владислав','Борисов', 's1mple.human'),(2, 'ls@borisof.ru', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','Владислав','Борисов', 's1mple.human'),(3, 'offers@borisof.ru', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','Владислав','Борисов', 's1mple.human');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (2, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (3, 'ROLE_USER');