package technology.weaviate;

import technology.semi.weaviate.client.Config;
import technology.semi.weaviate.client.WeaviateClient;
import technology.semi.weaviate.client.base.Result;
import technology.semi.weaviate.client.v1.batch.model.ObjectGetResponse;
import technology.semi.weaviate.client.v1.data.model.WeaviateObject;
import technology.semi.weaviate.client.v1.misc.model.Meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Config config = new Config("http", "localhost:8080");
        WeaviateClient client = new WeaviateClient(config);
        Result<Meta> meta = client.misc().metaGetter().run();
        if (meta.getError() == null) {
            System.out.printf("meta.hostname: %s\n", meta.getResult().getHostname());
            System.out.printf("meta.version: %s\n", meta.getResult().getVersion());
            System.out.printf("meta.modules: %s\n", meta.getResult().getModules());
        } else {
            System.out.printf("Error: %s\n", meta.getError().getMessages());
        }
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("title", "Sam");
        props.put("content", "Sam");
        props.put("wordCount", 9223372036854775807L);
        WeaviateObject wObj = WeaviateObject.builder().className("Article").properties(props).build();
        WeaviateObject[] objects = new WeaviateObject[]{wObj};

        Result<ObjectGetResponse[]> result = client.batch().objectsBatcher().withObjects(objects).run();

        if (result.hasErrors()) {
            System.out.println("Error: " + result.getError());
            return;
        }
        System.out.println("Result: " + result);
        System.out.println("getResult(): " + result.getResult());
        System.out.println("getResult().length: " + result.getResult().length);
        System.out.println("hashCode: " + result.getResult().hashCode());
        System.out.println("stream stuff: " + Arrays.stream(result.getResult()).toList().get(0).getId());
        System.out.println("wordcount: " + Arrays.stream(result.getResult()).toList().get(0).getProperties().get("wordCount"));

    }
}