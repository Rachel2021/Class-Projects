% Code is to broken up into sections so that it can be manually run
% piece-by-piece during the Live Demo according to at what pace the demo is 
% proceeding (e.g. can pasue and not run next section if someone is asking 
% a question)

%% Phase 1: Set up MYO

% this adds directories to MATLAB search path
% needs to run once before you run program

install_myo_mex; % additionally saves the path
sdk_path = 'C:\MYO-SDK'; % root path to Myo SDK
build_myo_mex(sdk_path); % builds myo_mex

%% Phase 2: Continue MYO setup
% must wait until the above finishes running before running this section

countMyos = 1;
mm = MyoMex(countMyos);
m1 = mm.myoData(1);

% m1.clearLogs();

%% Phase 3: Add the 3 mydaq sessions 

% session 1: digital input from the push button < can be analog if needed
% session 2: analog output channel, to move the wizard's wand (leg)
% session 3: counter output channel, for motor to move the enemy
% session 4: counter output channel, from 2nd mydaq, to move wacker motor

% create session for push button
s1 = daq.createSession('ni');
addDigitalChannel(s1, 'myDAQ1', 'port0/line0', 'InputOnly');
data = inputSingleScan(s1);

% create session for controlling the leg
s2 = daq.createSession('ni');
s2.Rate = 14000;
addAnalogOutputChannel(s2, 'myDAQ1', 0, 'Voltage');
time = 0.6;
t = linspace(0, 2*pi*time, s2.Rate*time);
s2.IsContinuous = true;
 
% create session for controlling the motor
% called pwm1
s3 = daq.createSession('ni');
s3.Rate = 1000;
addCounterOutputChannel(s3,'myDAQ1', 0, 'PulseGeneration');
s3.Channels(1).Terminal;
pwm1 = s3.Channels(1);
pwm1.InitialDelay = 0;
s3.IsContinuous = true;
startBackground(s3);
pwm1.Frequency = 50;
pwm1.DutyCycle = 0.07;

% If the wire comes out the back of the black board, then:
% range: 0.03 (faces front towards us) to 0.12 (faces outlets at back)
% 0.07 faces harry 

% create session for controlling second motor for wacker
% called pwm2
s4 = daq.createSession('ni');
s4.Rate = 1000;
addCounterOutputChannel(s4,'myDAQ2', 0, 'PulseGeneration');
s4.Channels(1).Terminal;
pwm2 = s4.Channels(1);
pwm2.InitialDelay = 0;
s4.IsContinuous = true;
startBackground(s4);
pwm2.Frequency = 50;
pwm2.DutyCycle = 0.11;

%% Phase 4: Run sensor collection and respond accordingly

% Step 0: reset servo
pwm2.DutyCycle = 0.11;

% Step 1: check pushb+utton
check = 0;
check = inputSingleScan(s1);

% if it exits this loop, that means button was pressed
while (check == 0) 
    pause(0.5);
    check = inputSingleScan(s1);
end

disp("button pressed");
%pwm1.DutyCycle = 0.1;
%pwm2.DutyCycle = 0.1;

% Step 2: do myo data collection and analysis
m1.stopStreaming();
m1.clearLogs();

spell = 0;
m1.startStreaming();
pause(5);
m1.stopStreaming();

% acceleration:
a = m1.accel_log;
time = m1.timeIMU_log;
adata = (sqrt(sum(a'.^2)))'; 
maxacc = max(adata);

% If the max is above 2.9 -> Avada Kedavra
% If between 1.1 and 2 -> dancing spell spell
% speed was just around 1.0. 
% Make sure they only press button just before casting spell, and are
% perfectly still before and when starting to cast spell

% emg: greater than 0.4
emg_data = m1.emg_log;
data = emg_data(:,4);
maxemg = max(data);
integral = trapz(abs(data));

% spell = 0 -> no spell, 1 -> AK, spell = 2 -> dance
% disp(maxacc);
disp(integral);
if (integral > 60.0) 
    if (maxacc > 2.9)
        spell = 1;
    elseif (maxacc > 1.1) 
        spell = 2;
    else
        spell = 0;
    end
end


% Step 3: Respond
if (spell == 1) % Avada Kedavra
    
    % first response: extend leg
    outputSignal1 = 1.5*(sin(5000*t));
    queueOutputData(s2, [outputSignal1]');
    s2.startBackground();
    pause(1);
    s2.stop();
    
    outputSignal2 = 1.5*(sin(200*t));
    queueOutputData(s2, [outputSignal2]');
    s2.startBackground();
    pause(1);
    
    % second response: servo motor #2 moves
    
    pwm2.DutyCycle = 0.05;
    pause(0.5);
    s2.stop();
    
    
elseif (spell == 2) % dancing
    
    % first response: extend leg
    outputSignal1 = 1.5*(sin(5000*t));
    queueOutputData(s2, [outputSignal1]');
    s2.startBackground();
    pause(1);
    s2.stop();
    
    outputSignal2 = 1.5*(sin(200*t));
    queueOutputData(s2, [outputSignal2]');
    s2.startBackground();
    pause(1);
    
    
    % second response: servo motor #1 moves
    dutyCycles = [0.07 0.06 0.07 0.05 0.055 0.04 0.045 0.035 0.04 0.035 0.07];
    for i=1:length(dutyCycles)
        pwm1.DutyCycle = dutyCycles(i);
        pause(0.5);
    end
   
    s2.stop();
    
else
    % nothing
end


%% Phase 5: End Myo collection
mm.delete;
clear mm;

%% Phase 6: Cleanup
outputSignal1 = 1.5*(sin(5000*t));
queueOutputData(s2, [outputSignal1]');
s2.startBackground();
