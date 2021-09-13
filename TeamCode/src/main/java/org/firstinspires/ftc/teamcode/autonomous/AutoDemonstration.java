package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "AutoDemonstration", group = "autonomous")
public class AutoDemonstration extends LinearOpMode {

    public static String CLASS_NAME = "AutoDemonstration";
// your mother
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DistanceSensor distanceSensor;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        distanceSensor = hardwareMap.get(DistanceSensor.class, "sensorRange");

        addAndUpdate(CLASS_NAME + " : finished init");

        waitForStart();

        double globalPower = 0.5;

        drive(globalPower);

        // distance to travel in feet
        double distanceToMove = 2;

        // will get caught in loop while we haven't moved distanceToMove
        while( distanceSensor.getDistance(DistanceUnit.INCH) > distanceToMove * 12 )
            addAndUpdate( "Currently " + distanceSensor.getDistance(DistanceUnit.INCH) + " inches away from target");

        stopMoving( );

        addAndUpdate( "finished" );
    }

    public void addAndUpdate( String text ) {
        telemetry.addLine( text );
        telemetry.update();

    }

    public void stopMoving( ) {
        drive( -1 );
        sleepRobot( 100 );
        drive( 0 );
    }

    public void sleepRobot(long time) {
        long startTime = System.currentTimeMillis();
        while (startTime + time > System.currentTimeMillis()) ;
    }

    /**
     * drive the robot using a designated power
     * @param power to drive the robot (+ is forward, - is backward)
     */
    public void drive(double power) {
        setPowers(power, power, power, power);
    }

    /**
     * strafe the robot using designated power
     * @param power to strafe the robot (+ is right, - is left)
     */
    public void strafe(double power) {

        setPowers( power, -power, -power, power );
    }

    /**
     * rotate the robot using designated power
     * @param power to rotate the robot (+ is right, - is left)
     */
    public void rotate(double power) {

        setPowers( -power, -power, power, power );
    }

    public void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        telemetry.addLine("FL: " + frontLeftPower + ", FR: " + frontRightPower);
        telemetry.addLine("BL: " + backLeftPower + ", BR: " + backRightPower);
        telemetry.update();

    }

}
