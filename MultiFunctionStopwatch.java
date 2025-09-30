import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class MultiFunctionStopwatch extends JFrame {
    // Stopwatch and Timer variables
    private long startTime;
    private boolean running = false;
    private Timer stopwatchTimer;
    private int countdownSeconds = 0;

    // UI Components
    private JLabel stopwatchLabel;
    private JLabel timerLabel;
    private JTextField timerInputField;
    private JButton startStopwatchButton, resetStopwatchButton;
    private JButton startTimerButton, stopTimerButton, resetTimerButton;
    private JTextArea healthDataArea;
    private JTextArea weatherDataArea;

    public MultiFunctionStopwatch() {
        // Window setup
        setTitle("Multi-Function Stopwatch");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Panels for Stopwatch, Timer, Health, and Weather
        JPanel stopwatchPanel = createStopwatchPanel();
        JPanel timerPanel = createTimerPanel();
        JPanel healthPanel = createHealthPanel();
        JPanel weatherPanel = createWeatherPanel();

        // Add panels to the frame
        add(stopwatchPanel, BorderLayout.NORTH);
        add(timerPanel, BorderLayout.CENTER);
        add(healthPanel, BorderLayout.SOUTH);
        add(weatherPanel, BorderLayout.EAST);
    }

    // Stopwatch Panel
    private JPanel createStopwatchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        stopwatchLabel = new JLabel("00:00:00");
        stopwatchLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        panel.add(stopwatchLabel);

        startStopwatchButton = new JButton("Start");
        startStopwatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (running) {
                    stopStopwatch();
                } else {
                    startStopwatch();
                }
            }
        });
        panel.add(startStopwatchButton);

        resetStopwatchButton = new JButton("Reset");
        resetStopwatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetStopwatch();
            }
        });
        panel.add(resetStopwatchButton);

        return panel;
    }

    // Timer Panel
    private JPanel createTimerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        timerLabel = new JLabel("Timer: 00:00");
        timerLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        panel.add(timerLabel);

        timerInputField = new JTextField(5);
        panel.add(timerInputField);

        startTimerButton = new JButton("Start Timer");
        startTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });
        panel.add(startTimerButton);

        stopTimerButton = new JButton("Stop Timer");
        stopTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });
        panel.add(stopTimerButton);

        resetTimerButton = new JButton("Reset Timer");
        resetTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });
        panel.add(resetTimerButton);

        return panel;
    }

    // Health Panel
    private JPanel createHealthPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        healthDataArea = new JTextArea();
        healthDataArea.setEditable(false);
        healthDataArea.setText("Health Measurements:\nHeart Rate: 75 bpm\nSteps: 1500\nSpo2: 99%");
        panel.add(new JScrollPane(healthDataArea), BorderLayout.CENTER);

        return panel;
    }

    // Weather Panel
    private JPanel createWeatherPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        weatherDataArea = new JTextArea();
        weatherDataArea.setEditable(false);
        weatherDataArea.setText("Weather:\nLocation: New York\nTemp: 15°C\nCondition: Sunny");
        panel.add(new JScrollPane(weatherDataArea), BorderLayout.CENTER);

        return panel;
    }

    // Stopwatch Methods
    private void startStopwatch() {
        running = true;
        startStopwatchButton.setText("Stop");
        startTime = System.currentTimeMillis() - (countdownSeconds * 1000); // Adjust for any previous time
        stopwatchTimer = new Timer();
        stopwatchTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                int hours = (int) (elapsed / 3600000);
                int minutes = (int) ((elapsed % 3600000) / 60000);
                int seconds = (int) ((elapsed % 60000) / 1000);
                stopwatchLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }
        }, 0, 1000);
    }

    private void stopStopwatch() {
        running = false;
        startStopwatchButton.setText("Start");
        stopwatchTimer.cancel();
    }

    private void resetStopwatch() {
        running = false;
        startStopwatchButton.setText("Start");
        stopwatchLabel.setText("00:00:00");
        if (stopwatchTimer != null) stopwatchTimer.cancel();
    }

    // Timer Methods
    private void startTimer() {
        try {
            countdownSeconds = Integer.parseInt(timerInputField.getText()) * 60; // Convert to seconds
            timerLabel.setText("Timer: " + formatTime(countdownSeconds));
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    countdownSeconds--;
                    if (countdownSeconds <= 0) {
                        timerLabel.setText("Timer: 00:00");
                        cancel(); // Stop the timer when it reaches zero
                    } else {
                        timerLabel.setText("Timer: " + formatTime(countdownSeconds));
                    }
                }
            }, 0, 1000);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    private void stopTimer() {
        countdownSeconds = 0;
    }

    private void resetTimer() {
        countdownSeconds = 0;
        timerLabel.setText("Timer: 00:00");
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MultiFunctionStopwatch frame = new MultiFunctionStopwatch();
                frame.setVisible(true);
            }
        });
    }
}
