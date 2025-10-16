package discountAutoParts.exception;

import discountAutoParts.models.ErrorResponse;

public class OutOfStockException extends RuntimeException{

    ErrorResponse errorResponse;

    public OutOfStockException(ErrorResponse errorResponse) {
        super(errorResponse.getErrorMessage());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
