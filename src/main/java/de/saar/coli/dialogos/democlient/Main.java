/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.saar.coli.dialogos.democlient;

import com.clt.dialog.client.Client;
import com.clt.dialog.client.ConnectionState;
import com.clt.script.exp.values.StringValue;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author koller
 */
public class Main extends Client {
    private ClientFrame f;
    
    public Main() throws IOException {
        Image image = ImageIO.read(getClass().getResource("light-bulb-on-off.png"));
        
        f = new ClientFrame(image);
        f.setLightState(false);
        f.setVisible(true);
    }
    
    @Override
    public void stateChanged(ConnectionState cs) {
        f.setState(cs.toString());
    }

    @Override
    public void sessionStarted() {
        System.err.println("session started: ");
    }

    @Override
    public void reset() {
        f.setLightState(false);
    }

    @Override
    public void output(com.clt.script.exp.Value value) {
        StringValue sv = (StringValue) value;
        String s = sv.getString().toLowerCase();
        
        if( "on".equals(s) ) {
            f.setLightState(true);
        } else if( "off".equals(s) ) {
            f.setLightState(false);
        } else {
            System.err.println("Warning: Ignored illegal light state '" + s + "'");
        }
    }

    @Override
    public String getName() {
        return "DialogOS Demo Client";
    }

    @Override
    public void error(Throwable thrwbl) {
        System.err.println("Demo client ERROR: " + thrwbl);
    }
    
    public static void main(String[] args) throws IOException {
        new Main().open(8888);
    }
}
