package els.model;

import els.main.ELSServer;
import els.main.EventMessage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Sensor2 extends JPanel implements ChangeListener, ActionListener {
	/**
	 * Derived from a class that implements Serializable
	 * In our case, the Serializable parent class is JPanel.
	 */
	private static final long serialVersionUID = -3035305620814885548L;
	private int id;
	private float value = 0;

	private boolean continuous = false;
	private boolean isFloat;
	private float vLow = 0;
	private float vHigh = 100;
	private String symbol = "";
	private String name = "";
	private JSlider slider = new JSlider();
	private JButton on = new JButton();
	private boolean controllable = false;
	private ELSServer server;
	private JLabel label;

	public Sensor2(ELSServer elsServer) {
		super(new BorderLayout());
		
		this.server = elsServer;
	}

	public void createControlPanel() {		
		if(controllable){
			if (continuous) {
				slider.setName(Integer.toString(id));
				slider.setMaximum((int) vHigh);
				slider.setMinimum((int) vLow);
				slider.setOrientation(JSlider.HORIZONTAL);
				slider.setMajorTickSpacing(20);
				slider.setMinorTickSpacing(10);
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
				slider.addChangeListener(this);
				this.add(new JLabel(name), BorderLayout.CENTER);
				this.add(slider, BorderLayout.SOUTH);
			} else {
				if(value == 0)
					on.setText("Turn ON");
				else
					on.setText("Turn OFF");
				on.addActionListener(this);
				this.add(new JLabel(name), BorderLayout.CENTER);
				this.add(on, BorderLayout.SOUTH);
			}
		}else{
			label = new JLabel(name + " " + getValue() + symbol);
			this.add(label);
		}

	}
	
	public void redoPanel(){
		if(controllable){
			if (continuous){
				this.slider.setValue((int) getValue());
			} else {
				if(value == 0)
					this.on.setText("Turn ON");
				else
					this.on.setText("Turn OFF");
			}
		} else {
			label.setText(name + " " + getValue() + symbol);
		}
		
		invalidate();
		revalidate();
		validate();
		repaint();
	}

	public void setContinuous(boolean cont) {
		this.continuous = cont;
	}
	
	public boolean getContinuous() {
		return this.continuous;
	}

	public void setFloat(boolean isFloat) {
		this.isFloat = isFloat;
	}
	
	public boolean isFloat() {
		return isFloat;
	}

	public void setVHigh(float vHigh) {
		this.vHigh = vHigh;
	}

	public void setVLow(float vLow) {
		this.vLow = vLow;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setLabelName(String name) {
		this.name = name;
	}

	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
		setName(Integer.toString(id));
	}

	public float getValue() {
		float v = this.value;
		if(isFloat)
			return v/10;
		return v;
	}
	
	public void setValue(float value) {
		this.value = value;
//		if(slider!=null)
//			slider.setValue((int) value);
//		if(on!=null)
//			on.setText(Integer.toString((int) value));
	}
	
	public String toString(){
		String a = "------------------SENSOR------------------\n";
		a+="Name: ";
		a+=name;
		a+= " ID: ";
		a+=id;
				
		a+="\n V_Low: ";
		a+=vLow;
		a+=" V_High: ";
		a+=vHigh;
		
		a+= "\n Cont: ";
		a+=continuous;
		a+=" Is Float: ";
		a+=isFloat;
		
		
		
		return a;
	}

	public boolean isControllable() {
		return controllable;
	}

	public void setControllable(boolean controllable) {
		this.controllable = controllable;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
	    if (!source.getValueIsAdjusting()) {
	    	EventMessage msg = new EventMessage();
	    	msg.setEventId(id);
	    	msg.setData(source.getValue());
	    	
	    	//server.updateAllOnServerEvent(msg.getMessageAsByteArray());
	    }
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
    	EventMessage msg = new EventMessage();
    	msg.setEventId(id);
    	
		if(value == 0){
			value = 100;
			on.setText("Turn OFF");
	    	msg.setData(100);
		} else {
			value = 0;
			on.setText("Turn OFF");
	    	msg.setData(0);
		}
    	//server.updateAllOnServerEvent(msg.getMessageAsByteArray());
	}
}
