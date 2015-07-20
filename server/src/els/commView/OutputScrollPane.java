package els.commView;


import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * Created by Filip on 2015-07-18.
 */
public class OutputScrollPane extends ScrollPane implements Runnable {
    private TextArea outputTextArea;

    @Override
    public void run() {

    }


/*
    public OutputScrollPane(TextArea mOutputField) {
        this.mOutputField = mOutputField;
        //mOutputField = new JTextPane();
        mOutputField.setEditable(false);
        mOutputField.setBackground(new Color(245, 245, 245));
        mOutputDocument = mOutputField.getStyledDocument();

        Color borderhighlightouter = new Color(220, 220, 220);
        Color borderhighlightinner = new Color(170, 170, 170);
        Color bordershadowouter = new Color(120, 120, 120);
        Color bordershadowinner = new Color(170, 170, 170);

        // mOutputScroll = new JScrollPane(mOutputField);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setPreferredSize(new Dimension(250, 400));
        this.setMinimumSize(new Dimension(50, 50));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0), BorderFactory.createBevelBorder(BevelBorder.LOWERED, borderhighlightouter, borderhighlightinner, bordershadowouter, bordershadowinner)));

    }

    private final boolean shouldScroll() {
        int min = this.getVerticalScrollBar().getValue() + this.getVerticalScrollBar().getVisibleAmount();
        int max = this.getVerticalScrollBar().getMaximum();

        return min == max;
    }


    private boolean isViewAtBottom() {
        JScrollBar sb = this.getVerticalScrollBar();
        int min = sb.getValue() + sb.getVisibleAmount();
        int max = sb.getMaximum();
        //System.out.println(min + " " + max);
        return min == max;
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
                    }
                });
    }

    public void run() {
        while (true) {
            if (shouldScroll()) {
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
                            }
                        });
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


    public void displayMessage(String message) {
        synchronized (this) {
            displayMessage(message, "");
        }
    }

    public void displayMessage(String message, String style) {
        displayMessage(message, style, true);
    }

    public void displayMessage(String message, String style, boolean prependnewline) {
        String newline = (prependnewline ? "\n" : "");
        boolean scroll = isViewAtBottom() && prependnewline;

        style = (style.equals("") ? "regular" : style);
        message = message.replace("\n", " ");

        try {
            mOutputDocument.insertString(mOutputDocument.getLength(),
                    String.format("%s%s", newline, message),
                    mOutputDocument.getStyle(style));
        } catch (Exception e) {
        }

        if (scroll) {
            scrollToBottom();
        }
    }*/
}
