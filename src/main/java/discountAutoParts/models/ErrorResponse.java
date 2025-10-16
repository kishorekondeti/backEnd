package discountAutoParts.models;

public class ErrorResponse {
    private String status;
    private String errorCode;
    private String errorMessage;

    public ErrorResponse(String status, String errorCode, String errorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
