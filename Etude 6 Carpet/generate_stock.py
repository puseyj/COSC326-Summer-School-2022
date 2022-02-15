import random
import string

def upper_string(length=4): # define the function and pass the length as argument  
    # Print the string in Uppercase
    for _ in range(25):
        result = ''.join((random.choice(string.ascii_uppercase) for x in range(length))) # run the loop until the define length  
        # result.encode('utf8')
        result.encode('ascii')
        print(result)


if __name__ == '__main__':
    upper_string()