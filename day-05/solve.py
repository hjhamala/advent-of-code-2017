import numpy as np
import traceback


def get_commands():
    return np.loadtxt("input.txt", delimiter="\n")


def walk(commands):
    index = 0
    steps = 0
    command_alterations = {}
    try:
        while True:
            value = int(commands[index])
            if index in command_alterations:
                command_alt_prev = command_alterations[index]
                command_alterations[index] +=1
                index += command_alt_prev + value
            else:
                command_alterations[index]=1
                index += value
            steps += 1
    except Exception:
        traceback.print_exc()
        return steps


def walk_2(commands):
    index = 0
    steps = 0
    command_alterations = {}
    try:
        while True:
            value = int(commands[index])
            if index in command_alterations:
                command_alt_prev = command_alterations[index]
                if command_alt_prev + value >= 3:
                    command_alterations[index] -=1
                else:
                    command_alterations[index] +=1
                index += command_alt_prev + value
            elif value >= 3:
                command_alterations[index]=-1
                index += value
            else:
                command_alterations[index]=1
                index += value
            steps += 1
    except Exception:
        traceback.print_exc()
        return steps