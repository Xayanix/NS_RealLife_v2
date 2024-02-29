package net.xayanix.nssv.reallife.mongodb;

import com.mongodb.async.client.FindIterable;
import lombok.AccessLevel;
import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bson.Document;

public abstract class SerializeObject {

    private final String collectionName;
    private final String key;
    @Getter(AccessLevel.PROTECTED)
    private final String keyValue;

    public SerializeObject(String collectionName, String key, String keyValue) {
        this.collectionName = collectionName;
        this.key = key;
        this.keyValue = keyValue;
    }

    @Deprecated
    public void find(){
        FindIterable<Document> iterable = MongoConnection.getInstance().getMongoDatabase().getCollection(this.collectionName).find(new Document(this.key, this.keyValue));
        iterable.forEach(document -> {
        }, (result, t) -> { });
    }

    public void delete() {
        MongoConnection.getInstance().getMongoDatabase().getCollection(this.collectionName).deleteMany(new Document(this.key, this.keyValue), (deleteResult, throwable) -> {
            System.out.println(deleteResult.getDeletedCount());
            if (throwable == null) {
                return;
            }
            throwable.printStackTrace();
        });


    }

    public void insert() {
        MongoConnection.getInstance().getMongoDatabase().getCollection(this.collectionName).insertOne(Document.parse(MongoConnection.getInstance().getGson().toJson(this)), (aVoid, throwable) -> {
            if (throwable == null) {
                return;
            }
            throwable.printStackTrace();
        });
    }

    public void synchronize() {
        MongoConnection.getInstance().getMongoDatabase().getCollection(this.collectionName).findOneAndReplace(new Document(this.key, this.keyValue), Document.parse(MongoConnection.getInstance().getGson().toJson(this)), (aVoid, throwable) -> {
            if (throwable == null) {
                return;
            }
            throwable.printStackTrace();
        });
    }

}