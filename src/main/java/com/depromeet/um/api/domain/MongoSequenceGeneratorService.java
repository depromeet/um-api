package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.MongoSequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;


@Service
public class MongoSequenceGeneratorService {
    private final MongoOperations mongoOperations;

    public MongoSequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        MongoSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                MongoSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
