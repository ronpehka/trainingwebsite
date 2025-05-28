-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-16 08:39:30.912

-- tables
-- Table: coach_sport
CREATE TABLE coach_sport (
                             id serial  NOT NULL,
                             coach_user_id int  NOT NULL,
                             sport_id int  NOT NULL,
                             CONSTRAINT coach_sport_pk PRIMARY KEY (id)
);

-- Table: district
CREATE TABLE district (
                          id serial  NOT NULL,
                          name varchar(255)  NOT NULL,
                          CONSTRAINT district_pk PRIMARY KEY (id)
);

-- Table: location
CREATE TABLE location (
                          id serial  NOT NULL,
                          name varchar(255)  NOT NULL,
                          district_id int  NOT NULL,
                          address varchar(255)  NOT NULL,
                          lat decimal(18,15)  NULL,
                          long decimal(18,15)  NULL,
                            openinghours varchar(255) NULL,
                            imageurl varchar(500) NULL,
                          CONSTRAINT location_pk PRIMARY KEY (id)
);

-- Table: location_image
CREATE TABLE location_image (
                                id serial  NOT NULL,
                                location_id int  NOT NULL,
                                data bytea  NOT NULL,
                                CONSTRAINT location_image_pk PRIMARY KEY (id)
);

-- Table: mailbox
CREATE TABLE mailbox (
                         id serial  NOT NULL,
                         sender_user_id int  NOT NULL,
                         receiver_user_id int  NOT NULL,
                         title varchar(255)  NOT NULL,
                         body varchar(500)  NOT NULL,
                         status char(3)  NOT NULL,
                         CONSTRAINT mailbox_pk PRIMARY KEY (id)
);

-- Table: profile
CREATE TABLE profile (
                         id serial  NOT NULL,
                         user_id int  NOT NULL,
                         first_name varchar(255)  NOT NULL,
                         last_name varchar(255)  NOT NULL,
                         description varchar(500)  NULL,
                         email varchar(255)  NULL,
                         phone varchar(255)  NULL,
                         date_of_birth date NOT NULL ,
                         gender char(1)  NULL,
                         CONSTRAINT profile_pk PRIMARY KEY (id)
);

-- Table: register
CREATE TABLE register (
                          id serial  NOT NULL,
                          training_id int  NOT NULL,
                          user_id int  NOT NULL,
                          status varchar(1)  NOT NULL,
                          date date  NOT NULL,
                          CONSTRAINT register_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
                      role_id serial  NOT NULL,
                      name varchar(255)  NOT NULL,
                      CONSTRAINT id PRIMARY KEY (role_id)
);

-- Table: sport
CREATE TABLE sport (
                       id serial  NOT NULL,
                       name varchar(255)  NOT NULL,
                       CONSTRAINT sport_pk PRIMARY KEY (id)
);

-- Table: training
CREATE TABLE training (
                          id serial  NOT NULL,
                          coach_user_id int  NOT NULL,
                          sport_id int  NOT NULL,
                          name varchar(255)  NOT NULL,
                          description varchar(1000)  NOT NULL,
                          gender char(1)  NOT NULL,
                          start_date date  NOT NULL,
                          end_date date  NOT NULL,
                          start_time time  NOT NULL,
                          end_time time  NOT NULL,
                          max_limit int  NOT NULL,
                          status varchar(1)  NOT NULL,
                          CONSTRAINT training_pk PRIMARY KEY (id)
);

-- Table: training_date
CREATE TABLE training_date (
                               id serial  NOT NULL,
                               training_id int  NOT NULL,
                               date date  NOT NULL,
                               CONSTRAINT training_date_pk PRIMARY KEY (id)
);

-- Table: training_location
CREATE TABLE training_location (
                                   id serial  NOT NULL,
                                   training_id int  NOT NULL,
                                   location_id int  NOT NULL,
                                   status char(1)  NOT NULL,
                                   CONSTRAINT training_location_pk PRIMARY KEY (id)
);

-- Table: training_weekday
CREATE TABLE training_weekday (
                                  id serial  NOT NULL,
                                  training_id int  NOT NULL,
                                  weekday_id int  NOT NULL,
                                  CONSTRAINT training_weekday_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
                        id serial  NOT NULL,
                        role_id int  NOT NULL,
                        email varchar(255)  NOT NULL,
                        password varchar(255)  NOT NULL,
                        status varchar(1)  NOT NULL,
                        CONSTRAINT user_pk  PRIMARY KEY (id)
);

-- Table: weekday
CREATE TABLE weekday (
                         id serial  NOT NULL,
                         short char(1)  NOT NULL,
                         number int  NOT NULL,
                         CONSTRAINT weekday_pk PRIMARY KEY (id)
);



-- Table: coach_image
CREATE TABLE coach_image (
                                id serial  NOT NULL,
                                user_id int  NOT NULL,
                                data bytea  NOT NULL,
                                CONSTRAINT coach_image_pk PRIMARY KEY (id)
);



-- foreign keys
-- Reference: coach_sport_sport (table: coach_sport)
ALTER TABLE coach_sport ADD CONSTRAINT coach_sport_sport
    FOREIGN KEY (sport_id)
        REFERENCES sport (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: coach_image (table: coach_image)
ALTER TABLE coach_image ADD CONSTRAINT coach_image
    FOREIGN KEY (user_id)
        REFERENCES "user"(id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE

;

-- Reference: coach_sport_user (table: coach_sport)
ALTER TABLE coach_sport ADD CONSTRAINT coach_sport_user
    FOREIGN KEY (coach_user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: location_district (table: location)
ALTER TABLE location ADD CONSTRAINT location_district
    FOREIGN KEY (district_id)
        REFERENCES district (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: location_image_location (table: location_image)
ALTER TABLE location_image ADD CONSTRAINT location_image_location
    FOREIGN KEY (location_id)
        REFERENCES location (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;


-- Reference: mailbox_user_receiver (table: mailbox)
ALTER TABLE mailbox ADD CONSTRAINT mailbox_user_receiver
    FOREIGN KEY (receiver_user_id)
        REFERENCES coach_image (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: mailbox_user_sender (table: mailbox)
ALTER TABLE mailbox ADD CONSTRAINT mailbox_user_sender
    FOREIGN KEY (sender_user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: profile_user (table: profile)
ALTER TABLE profile ADD CONSTRAINT profile_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: register_training (table: register)
ALTER TABLE register ADD CONSTRAINT register_training
    FOREIGN KEY (training_id)
        REFERENCES training (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: register_user (table: register)
ALTER TABLE register ADD CONSTRAINT register_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_date_training (table: training_date)
ALTER TABLE training_date ADD CONSTRAINT training_date_training
    FOREIGN KEY (training_id)
        REFERENCES training (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_location_location (table: training_location)
ALTER TABLE training_location ADD CONSTRAINT training_location_location
    FOREIGN KEY (location_id)
        REFERENCES location (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_location_training (table: training_location)
ALTER TABLE training_location ADD CONSTRAINT training_location_training
    FOREIGN KEY (training_id)
        REFERENCES training (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_sport (table: training)
ALTER TABLE training ADD CONSTRAINT training_sport
    FOREIGN KEY (sport_id)
        REFERENCES sport (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_user (table: training)
ALTER TABLE training ADD CONSTRAINT training_user
    FOREIGN KEY (coach_user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_weekday_training (table: training_weekday)
ALTER TABLE training_weekday ADD CONSTRAINT training_weekday_training
    FOREIGN KEY (training_id)
        REFERENCES training (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: training_weekday_weekday (table: training_weekday)
ALTER TABLE training_weekday ADD CONSTRAINT training_weekday_weekday
    FOREIGN KEY (weekday_id)
        REFERENCES weekday (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_role (table: user)
ALTER TABLE "user" ADD CONSTRAINT user_role
    FOREIGN KEY (role_id)
        REFERENCES role (role_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

