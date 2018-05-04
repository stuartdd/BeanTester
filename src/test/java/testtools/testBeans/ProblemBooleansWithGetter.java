package testtools.testBeans;

public class ProblemBooleansWithGetter {
        private final StringBuilder log = new StringBuilder();
        private boolean booleanPrimitive;
        private Boolean booleanObject;

        public boolean getBooleanPrimitive() {
            log.append("getBooleanPrimitive()|");
            return booleanPrimitive;
        }

        public void setBooleanPrimitive(boolean booleanPrimitive) {
            log.append("setBooleanPrimitive()|");
            this.booleanPrimitive = booleanPrimitive;
        }

        public Boolean getBooleanObject() {
            log.append("getBooleanObject()|");
            return booleanObject;
        }

        public void setBooleanObject(Boolean booleanObject) {
            log.append("setBooleanObject()|");
            this.booleanObject = booleanObject;
        }

        @Override
        public String toString() {
            return "ProblemBooleansWithGetter{" + log + '}';
        }
}
