-- ----------------------------------------------------------------------------
-- Model
-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS Inscription;
DROP TABLE IF EXISTS Race;

-- Create new tables

CREATE TABLE Race( idrace BIGINT NOT NULL AUTO_INCREMENT,
                 city VARCHAR(100) NOT NULL,
                 description VARCHAR(255) NOT NULL,
                 startDateTime DATETIME NOT NULL,
                 price INT NOT NULL,
                 participants INT,
                 maxParticipants INT NOT NULL,
                 addedDateTime DATETIME NOT NULL,
                 CONSTRAINT RacePk PRIMARY KEY (idrace),
                 CONSTRAINT validRacePrice check (price>=0),
                 CONSTRAINT validMaxPart check (maxParticipants>0));

CREATE TABLE Inscription (idinscription BIGINT NOT NULL AUTO_INCREMENT,
                         user VARCHAR(100) NOT NULL,
                         creditCardNumber VARCHAR(16) NOT NULL,
                         idrace BIGINT NOT NULL,
                         inscriptionDateTime DATETIME NOT NULL,
                         runnerNumber INT NOT NULL,
                         isNumberTaken boolean,
                         CONSTRAINT InscriptionPK PRIMARY KEY (idinscription),
                         CONSTRAINT RaceFK foreign key (idrace)
                            REFERENCES Race(idrace) ON DELETE CASCADE);