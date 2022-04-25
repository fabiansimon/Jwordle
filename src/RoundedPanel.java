import javax.swing.*;
import java.awt.*;

class RoundedPanel extends JPanel {

        private Color backgroundColor;
        private int cornerRadius;

        public RoundedPanel(int cornerRadius, Color backgroundColor) {
        super();
                this.cornerRadius = cornerRadius;
                this.backgroundColor = backgroundColor;
        }

        public void setBackground(Color color) {
                this.backgroundColor = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                Dimension arcs = new Dimension(cornerRadius, cornerRadius);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (backgroundColor != null) {
                        graphics.setColor(backgroundColor);
                } else {
                        graphics.setColor(getBackground());
                }

                graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
                graphics.setColor(getForeground());
                }
        }