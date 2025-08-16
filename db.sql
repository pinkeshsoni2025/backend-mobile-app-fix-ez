SELECT * FROM db.roles;

INSERT INTO db.roles(name) VALUES('ROLE_USER');
INSERT INTO db.roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO db.roles(name) VALUES('ROLE_ADMIN');

ALTER TABLE alpha_easy_fix.comment
    add COLUMN created_by varchar(20) NOT NULL,
    add COLUMN ticket_id BIGINT;

-- Add foreign key constraint for created_by
ALTER TABLE alpha_easy_fix.comment
    ADD CONSTRAINT fk_comment_created_by
    FOREIGN KEY (created_by)
    REFERENCES users(username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- Add foreign key constraint for assigned_to
ALTER TABLE alpha_easy_fix.comment
    ADD CONSTRAINT fk_comment_ticket_id1
    FOREIGN KEY (ticket_id)
    REFERENCES ticket(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;
    
    ALTER TABLE alpha_easy_fix.ticket
    ADD CONSTRAINT fk_ticket_created_by
    FOREIGN KEY (created_by)
    REFERENCES users(username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;
    
    desc alpha_easy_fix.rating
    
    ALTER TABLE alpha_easy_fix.rating
    ADD CONSTRAINT fk_rating_ticket_id
    FOREIGN KEY ticket(id)
    REFERENCES users(username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;
    
    
    ALTER TABLE alpha_easy_fix.rating
    ADD CONSTRAINT fk_rating_created_by
    FOREIGN KEY (created_by)
    REFERENCES users(username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;
    
    ALTER TABLE alpha_easy_fix.ticket
    add COLUMN assign_to varchar(20) NOT NULL
    
    
    ALTER TABLE alpha_easy_fix.ticket
    ADD CONSTRAINT fk_ticket_assign_to
    FOREIGN KEY (assign_to)
    REFERENCES users(username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;
    
    
     ALTER TABLE alpha_easy_fix.users
    ADD CONSTRAINT fk_users_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES users(username)
    ON DELETE SET NULL
    ON UPDATE CASCADE;
    
    ALTER TABLE alpha_easy_fix.users
    ADD CONSTRAINT fk_users_created_by1
    FOREIGN KEY (created_by)
    REFERENCES users(username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;