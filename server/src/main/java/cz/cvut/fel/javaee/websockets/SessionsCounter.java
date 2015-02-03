/*
 * The MIT License (MIT)
 *
 * Copyright (c) <year> <copyright holders>

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cz.cvut.fel.javaee.websockets;

import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Websocket endpoint that acts like page visitors counter
 * @author vodamiro
 */
@ServerEndpoint("/sessionsCounter")
public class SessionsCounter {

    private static final ArrayList<Session> sessions =  new ArrayList<>();
    
    /**
     * Add sessions to list
     * @param session
     * @throws IOException 
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("{\"action\":\"debug\", \"content\":\"opened\"}");
        sessions.add(session);
        informAllAboutChange();
    }

    /**
     * Return string with information of number of all opened sessions
     * @param message
     * @return 
     */
    @OnMessage
    public String onMessage(String message) {
        return "{\"action\":\"count\", \"content\":"+Integer.toString(getSessionCount())+"}";
    }

    /**
     * Catch error
     * @param t Error parameter
     */
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    
    /**
     * Count number of all open sessions
     * @return 
     */
    private static int getSessionCount() {
        int active = 0;
        for (Session session: sessions) {
            if (session.isOpen()) {
                active++;
            }
        }

        return active;
    }

    /**
     * Send the number of open sessions to all open session
     * ( Information is send everytime when there is a change in number of open sessions by default)
     * @throws IOException 
     */
    private void informAllAboutChange() throws IOException  {
        int activeCount = getSessionCount();
        for (Session session: sessions) {
            if (session.isOpen()) {
                session.getBasicRemote().sendText("{\"action\":\"count\", \"content\":"+Integer.toString(activeCount)+"}");
            }
        }
    }
}

