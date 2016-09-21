package br.com.involves.poe.utils;

/**
 * Created by marcelo on 20/09/16.
 */
public class CreateHashId {

    private final String hash;

    public CreateHashId(String client, String hash, String endPoint) {

        StringBuilder sb = new StringBuilder();
        sb.append(client);
        sb.append(hash);
        sb.append(endPoint);

        this.hash = sb.toString();
    }

    @Override
    public String toString() {
        return hash;
    }
}
