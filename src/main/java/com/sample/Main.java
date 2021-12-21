package com.sample;

import java.awt.FlowLayout;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.KieSession;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;


/**
 * This is a sample class to launch a rule.
 */
public class Main {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
        	KieRuntimeLogger kLogger = ks.getLoggers().newFileLogger(kSession, "soda");
            // go !
        	kSession.insert(new GUI(kSession));  	
            kSession.fireAllRules();
            kLogger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static class GUI {
    	private JFrame frame;
    	private JPanel panel;
    	private KieSession kSession;
    	
    	public GUI(KieSession kSession) {    
    		this.kSession = kSession;
    		
    		frame = new JFrame();
    	}
    	
    	public void showResult(String[] pictures) {    		
    		panel = new JPanel();
    		
    		for(String picture : pictures) {
    			JLabel JLabelPicture = new JLabel(new ImageIcon("src/main/resources/images/" + picture + ".png"));
    			panel.add(JLabelPicture);
    		}
    		
    		panel.setLayout(new FlowLayout());
    		frame.add(panel);
    		frame.setTitle("What should I drink? (SODA POP EDITION)");
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setVisible(true);
    		frame.pack();
    	}

    	public void close() {
    		frame.setVisible(false);
    		frame = new JFrame();
    	}
    	
    	public String doChoice(KieRuntime krt, String question, String[] options) {
            int n = JOptionPane.showOptionDialog(frame,
                                                 question,
                                                 "Question",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 null,
                                                 options,
                                                 options[0]);
            if(n == 0) {
            	return "YES";
            }
            return "NO";
    	}
    }
}
