public class SniperShots implements GameMode {
	// gives more range for your shots
    public void onScore(GamePanel panel, Player scorer) {}
    public void onShoot(Ball ball, Player shooter) {
        ball.velocityX *= 1.2;
        ball.velocityY *= 1.2;
    }
}