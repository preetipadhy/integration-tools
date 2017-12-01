package com.amadeus.imdgenerator.parser;

import com.amadeus.imdgenerator.swagger.Method;
import com.amadeus.imdgenerator.swagger.Node;
import com.amadeus.imdgenerator.swagger.Path;
import com.amadeus.imdgenerator.swagger.Swagger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SwaggerParser {

    private JsonObject definitions = null;
    private Map<String, Node> definitionNodeMap = new HashMap<>();

    public Swagger parse(URL url) throws IOException {

        JsonReader reader = new JsonReader(new BufferedReader(
                new InputStreamReader(url.openStream())));

        JsonElement jsonElement = new JsonParser().parse(reader);

        return handleRootObjects(jsonElement);

    }

    private Swagger handleRootObjects(JsonElement jsonElement) {

        JsonObject root = jsonElement.getAsJsonObject();

        Swagger swagger = new Swagger();

        Set<String> rootMembers = root.keySet();

        for (String member : rootMembers) {

            switch (member) {
                case "swagger":
                    swagger.setVersion(root.getAsJsonPrimitive(member).getAsString());
                    break;
                case "info":
                    handleInfo(root.getAsJsonObject(member), swagger);
                    break;
                case "basePath":
                    swagger.setBasePath(root.getAsJsonPrimitive(member).getAsString());
                    break;
                case "paths":
                    handlePaths(root.getAsJsonObject(member), swagger);
                    break;
                case "definitions":
                    definitions = root.getAsJsonObject(member);
                    break;

            }
        }

        return swagger;

    }

    private Node getNodeFromDefinition(JsonObject definitionJsonObject) {
        Node node = new Node();
        node.setPath(".");
        return node;

    }

    private void handleInfo(JsonObject info, Swagger swagger) {

        Set<String> infoMembers = info.keySet();

        swagger.setInformation(swagger.new Information());

        for (String member : infoMembers) {

            switch (member) {
                case "description":
                    swagger.getInformation().setDescription(info.getAsJsonPrimitive(member).getAsString());
                    break;
                case "version":
                    swagger.getInformation().setSpecificationVersion(info.getAsJsonPrimitive(member).getAsString());
                    break;
                case "title":
                    swagger.getInformation().setTitle(info.getAsJsonPrimitive(member).getAsString());
                    break;

            }
        }

    }

    private void handlePaths(JsonObject paths, Swagger swagger) {
        Set<String> pathMembers = paths.keySet();

        int numPaths = paths.size();

        Path[] swaggerPaths = new Path[numPaths];

        int count = 0;

        for (String member : pathMembers) {

            Path path = new Path();

            path.setPath(member);

            handlePath(paths.getAsJsonObject(member), path);

            swaggerPaths[count++] = path;

        }

        swagger.setPaths(swaggerPaths);
    }

    private void handlePath(JsonObject path, Path pathObj) {

        Set<String> methods = path.keySet();

        int numMethods = methods.size();

        Method httpMethods[] = new Method[numMethods];

        for (String member : methods) {

            Method method = new Method();

            method.setMethod(member);

            handleMethod(path.getAsJsonObject(member), method);

        }

        pathObj.setMethods(httpMethods);

    }

    private void handleMethod(JsonObject method, Method methodObj) {

        Set<String> members = method.keySet();

        for (String member : members) {

            switch (member) {
                case "operationId":
                    methodObj.setOperationId(method.getAsJsonPrimitive(member).getAsString());
                    break;
                case "summary":
                    methodObj.setSummary(method.getAsJsonPrimitive(member).getAsString());
                    break;
                case "consumes":
                    methodObj.setConsumes(formatArrayAsString(method.get(member).getAsJsonArray()));
                    break;
                case "produces":
                    methodObj.setConsumes(formatArrayAsString(method.get(member).getAsJsonArray()));
                    break;
                case "parameters":
                    handleParameters(method.getAsJsonArray(member), methodObj);
                    break;
                case "responses":
                    handleResponses(method.getAsJsonObject(member), methodObj);
                    break;

            }


        }

    }

    private void handleResponses(JsonObject asJsonObject, Method methodObj) {
    }

    private void handleParameters(JsonArray asJsonObject, Method methodObj) {
    }

    private String formatArrayAsString(JsonArray jsonArray) {

        StringBuilder stringbuilder = new StringBuilder();

        for (JsonElement element : jsonArray) {
            stringbuilder.append("{");
            stringbuilder.append(element.getAsString());
            stringbuilder.append("}");
        }

        return stringbuilder.toString();

    }


}
