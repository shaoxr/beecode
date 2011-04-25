package com.newland.nac;

/**
 * 通信服务端和发起端的基本接口.
 */
public interface Connector {
    /**
     * 开始守候处理，并且马上返回。（实现时需要创建新的守候线程）
     * 
     * @throws NacConfigException
     *             acceptor的配置问题.
     */
    void start() throws NacConfigException;

    /**
     * 如果需要签退已登录的会话，关闭连接，停止接受新的连接请求
     */
    void stop();

    /**
     * 停止所有的会话，可选是否等待所有的会话签退完成。
     * @param force 关闭连接之前是否等待签退完成.
     */
    public void stop(boolean force);

    /**
     * 开始接受连接，该方法将阻止线程，知道其它线程调用stop.
     * 
     *  @throws NacConfigException
     *             acceptor的配置问题.
     */
    void block() throws NacConfigException;

    /**
     * 检查是否存在会话
     * @return true 如果有会话存在则返回true, 否则返回false。
     */
    boolean hasSessions();
    
}