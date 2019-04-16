CREATE TABLE library (
                        id                      INTEGER PRIMARY KEY DEFAULT AUTO_INCREMENT,
                        author_first_name       VARCHAR(50) NOT NULL,
                        author_second_name      VARCHAR(50) NOT NULL,
                        title                   VARCHAR(200) NOT NULL,
                        isbn                    VARCHAR(13) NOT NULL UNIQUE,
                        year                    INT NOT NULL,
                        price                   FLOAT NOT NULL
);