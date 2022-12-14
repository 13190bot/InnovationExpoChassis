
# Problems 

drive forward and backwards, left right works fine but rotation shits itself


during rotation both left side motors should run opposite of both right side motors

our problem during rotation is that both front motors run opposite to the backwards motors

# Solution?

inversion issue?

did i initialize something wrong?

motors defined correctly?

could be the 2 fuses on 2 motors and none on the other 2?

# THINGS TO CHECK

## TRIPLE CHECK THE WIRING FOR THE MOTORS AND WHEEL CONFIGURATION
One way I like to check things is look at the lights on the motor controllers.

When going straight, 2 should be red, and 2 should be green.
When rotating, all 4 should be either red or green, depending on which 
direction you are rotating.
When strafing, 2 of the corners should be red, 
and the other 2 corners should be green.

1 More thing to check. When driving at a 45 degree angle, 
only 2 of the motors should be spinning, and they should be the 
2 perpendicular to the direction you want to move. If thats not 
working correctly, then its most likely that either motor reversing isnt 
right, or the PWM ports are wrong.



# independent motor control instructions
create an independent testing to see what each motor does when given input.


dpad_up = FrontLeft motor

dpad_down = FrontRight motor

dpad_right = BackRight motor

dpad_left = BackLeft motor

## NO MOTORS WERE REVERSED SO ALL WHEELS SHOULD BE MOVING IN THE SAME CLOCK-WISE DIRECTION WHEN ACTIVATED.

