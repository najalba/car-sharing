package carsharing.commands;

public class CarSharingCommandExecutor {
    private CarSharingCommand carSharingCommand;

    public void setCommand(CarSharingCommand carSharingCommand) {
        this.carSharingCommand = carSharingCommand;
    }

    public void execute() {
        this.carSharingCommand.execute();
    }
}
