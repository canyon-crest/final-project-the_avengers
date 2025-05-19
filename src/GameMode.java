public interface GameMode {
    void onScore(GamePanel panel, Player scorer);
    void onShoot(Ball ball, Player shooter);
}