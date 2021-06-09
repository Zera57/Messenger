public interface EventListener {
    void connectEvent(Connection connection);
    void disconnectEvent(Connection connection);
    void sendEvent(Connection connection, String value);
    void exceptionEvent(Connection connection, Exception e);
}
