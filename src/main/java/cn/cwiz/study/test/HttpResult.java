package cn.cwiz.study.test;

public class HttpResult {
    public String result;
    public int status;
    private HttpResult instance =null;

    HttpResult(){
        instance = (HttpResult) HttpResultProxy.getProxy(this);
    }


    public boolean isOK() {
        return (this.status == 200) || (this.status == 204);
    }

    public int getStatus() {
        return this.status;
    }

    public String getResult() {

        if (this.result != null) {
            return this.result;
        }
        else if (isOK()) {
            return "{\"state\":\"OK\"}";
        }
        else {
            return "{\"state\":\"Internal Error\"}";
        }
    }

    public static HttpResult OK() {
        HttpResult ok = new HttpResult();
        ok.result = "{\"state\":\"OK\"}";
        ok.status = 200;
        return ok;
    }

    public static HttpResult BadRequest(String msg) {
        if (msg == null) {
            msg = "Bad request";
        }
        HttpResult err = new HttpResult();
        err.result = "{\"state\":\"" + msg + "\"}";
        err.status = 400;
        return err;
    }

    public static HttpResult InternalError(String msg) {
        if (msg == null) {
            msg = "Internal error";
        }
        HttpResult err = new HttpResult();
        err.result = "{\"state\":\"" + msg + "\"}";
        err.status = 500;
        return err;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "result='" + result + '\'' +
                ", status=" + status +
                '}';
    }
}
