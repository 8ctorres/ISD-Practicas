namespace java es.udc.ws.runfic.thrift

struct ThriftRaceDto {
    1: i64 raceID;
    2: string city;
    3: string description;
    4: string startDateTime;
    5: double price;
    6: i32 participants;
    7: i32 maxParticipants;
}

struct ThriftInscriptionDto{
    1: i64 inscriptionID;
    2: string user;
    3: string creditCardNumber;
    4: i64 raceID;
    5: i32 runnerNumber;
    6: bool isNumberTaken;
}

exception ThriftInputValidationException {
    1: string message;
}

exception ThriftInstanceNotFoundException {
    1: string instanceId;
    2: string instanceType;
}

exception ThriftAlreadyInscribedException {
    1: string message;
}

exception ThriftInscriptionClosedException {
    1: string message;
}

exception ThriftInvalidUserException {
    1: string message;
}

exception ThriftNumberTakenException {
    1: string message;
}

exception ThriftRaceFullException {
    1: string message;
}

service ThriftRunficService{
    ThriftInscriptionDto inscribe(1: i64 raceID, 2: string email, 3: string creditCardNumber) throws (1: ThriftInputValidationException e, 2: ThriftInscriptionClosedException ee, 3: ThriftInstanceNotFoundException eee, 4: ThriftRaceFullException eeee, 5: ThriftAlreadyInscribedException eeeee)

    List<ThriftInscriptionDto> findAllFromUser(1: string email) throws (1: ThriftInputValidationException e)
}