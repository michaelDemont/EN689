# EN689

Application to verify compliance with the EN689 standard on workplace atmospheres
The standard EN689 is applied to determine whether the occupational risk for employees related to the exposure to chemical agents is suffiently controlled. 
In the workplace a series of atmospheric measurements is performed on employees of a homogeneous similar exposure group (SEG). 
A concentration can then be calculated on the basis of the amount of agent on the sampler and the volume of air drawn in. 
Next the goal is to compare these concentrations with the Belgian treshold limit value (TLV) in order to assess whether the legal restrictions are being respected.

In a first screen, the user is asked to provide the name of the firm and the number of measurements taken. 
To perform an evaluation a minimum of 3 measurements is necessary. 
If the name or the number of measurements is not given, an error appears on the screen. Same if the number is smaller than 3. 
If start is pressed the app will request an input of all measured concentrations. The values are each time confirmed with OK. 
Next the value of the treshold limit value of the chemical agent in question is required. 
After that a first evaluation is made. There are a few possibilities:

3 values: 
- all 3 values < 10% TLV => A green screen with Compliance is shown 
- 1 value > 100% TLV => A red screen with Non-Compliance is shown 
- 1 value < 100% TLV but > 10% TLV => Additional measurements are required 

 4 values: 
- all 4 values < 15% TLV => A green screen with Compliance is shown 
- 1 value > 100% TLV => A red screen with Non-Compliance is shown 
- 1 value < 100% TLV but > 15% TLV => Additional measurements are required 

5 values: 
- all 5 values < 20% TLV => A green screen with Compliance is shown 
- 1 value > 100% TLV => A red screen with Non-Compliance is shown 
- 1 value < 100% TLV but > 20% TLV => Additional measurements are required

6 values or more: 
- all 6 values < 20% TLV => A green screen with Compliance is shown 
- 1 value > 100% TLV => A red screen with Non-Compliance is shown 
- 1 value < 100% TLV but > 20% TLV => A statistical test is executed to decide if compliance requirements are met

The statistical test is based on the confidence interval. 
More specific a confidence interval of 70% that only 5% of the values will exceed the treshold limit value has to be achieved. 
The first step of the statistical test is to decide of the concentrations show a lognormal or rather a normal distribution. 
To make up your mind both graphs are shown on the screen. 
Afterwards the user has to pick the most linear graph as it represents the best fit. This choice is given by radio buttons after displaying both graphs. 
Based on this choice, a set of parameters is calculated and the green screen of compliance or red screen of non-conpliance appears. 
On the screen the parameters of the statistical on which this decision is based are shown as well. 
If the user thinks the wrong distribution is chosen, he/she can return with the back-button and alter his/her choice.

Because of the cost of these measurements, often only 6 measurements are executed. However there can always occur a technical problem. 
To make it possible to get an indication based on anly 5 measurements, an additional feature was added. 
In the start screen, the user can also press on the padlock icon. Before activating this option, a little riddle has to be solved to gain access. 
If the value is correct, the procedure for 5 values is followed but the mean value of these 5 concentrations will be automatically added as a 6th value. 
Standard deviation on the other hand is calculated only on the 5 original values.
