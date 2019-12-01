package smartAc.models;

import com.google.gson.JsonObject;

public class GoogleWebhookRequest {
    private String responseId;
    private QueryResult queryResult;

    public static class QueryResult {
        private String queryText;
        private JsonObject parameters;

        private QueryResult(String queryText, JsonObject parameters) {
            this.queryText = queryText;
            this.parameters = parameters;
        }

        public static class Builder {
            private String queryText;
            private JsonObject parameters;

            public Builder setQueryText(String queryText) {
                this.queryText = queryText;
                return this;
            }

            public Builder setParameters(JsonObject parameters) {
                this.parameters = parameters;
                return this;
            }

            public QueryResult build() {
                return new QueryResult(this.queryText, this.parameters);
            }
        }
    }

    private GoogleWebhookRequest(String responseId, QueryResult queryResult) {
        this.responseId = responseId;
        this.queryResult = queryResult;
    }

    public static class Builder {
        private String responseId;
        private QueryResult queryResult;

        public Builder setResponseId(String responseId) {
            this.responseId = responseId;
            return this;
        }

        public Builder setQueryResult(QueryResult queryResult) {
            this.queryResult = queryResult;
            return this;
        }

        public GoogleWebhookRequest build() {
            return new GoogleWebhookRequest(this.responseId, this.queryResult);
        }
    }

    public JsonObject getParameters() {
        return this.queryResult != null ? this.queryResult.parameters : null;
    }
}
