package br.com.involves.poe.exception;

public class HashNotFindException extends Exception {

    private String msg = null;

    public HashNotFindException(String msg) {
        this.msg = msg;
    }

    public HashNotFindException(String msg, String ... itens) {
        this.msg = msg;

        if( itens == null ){
            return;
        }

        for( int loop=0; loop < itens.length; loop++){
            this.msg = this.msg.replace("{".concat(String.valueOf(loop)).concat("}"), itens[loop]);
        }

    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
