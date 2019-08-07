import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class MyButton extends JButton {

    public MyButton(Image image) {
        super(new ImageIcon(image));
        initUI();
    }

    private void initUI() {
        BorderFactory.createLineBorder(Color.gray);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }
}
