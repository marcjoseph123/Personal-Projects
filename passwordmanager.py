# Marcus Joseph, Spring 2022
# ma684902

# function that finds the file that already contains users and passwords and adds them to the username_password_dict
def get_users_from_file():
    username_password_dict = {}
    file_name = input("What is the name of the storage file?\t")
    file = open(file_name, "r")
    contents_of_file = file.readlines()
    file.close()
    for user in (contents_of_file):
        user = user.strip("\n")
        user_and_pass = user.split()
        username = user_and_pass[0]
        password = user_and_pass[1]
        username_password_dict[username] = password
    return username_password_dict

# main menu function that shows the user the options that they have to choose from
def main_menu():
    print("Which operation do you want to preform?\n"
          "1. Register new user\n" 
          "2. Verify user sign in\n" 
          "3. Update username\n" 
          "4. Update password\n" 
          "5. Delete account\n"
          "6. Exit system\n")
    option = int(input("-Selection:\t"))
    check_user_option(option)
    return option

# all valid options the user can select and if its not one of these it prints to select only 1-6
def check_user_option(option):
    valid_options = [1,2,3,4,5,6]
    if option not in valid_options:
        print("\t- Yikes! That is not a vaild selection, please only select numbers 1-6.\n")

# function that is called when the user wants to register a new username and password    
def register_new_user(username_password_dict):
    is_user_valid = False
    while is_user_valid == False:
        username = input("-Username:\t")
        if username in username_password_dict:
            print("\t-That username is already in use. Please create a new one.")
        else:
            is_user_valid = is_username_valid(username)
            if is_user_valid == False:
                print("-Try again")
            else:
                break  

    # part of the fuction that takes care of the password
    is_pass_valid = False
    while is_pass_valid == False:
        password = input("-Password:\t")
        is_pass_valid = is_password_valid(password)
        if is_pass_valid == True:
            username_password_dict[username] = password
            print("\t-Account has been registered!")
        else:
            print("-Please try again")

# fuction that takes the username that the user inputs and passes it through checks to ensure that the username is valid
def is_username_valid(username):
    error_messages = []
    user_name_is_valid = True

    # checking lenth of username
    if len(username) < 6 or len(username) > 15:
        error_message1 = "\t-username must be between 6 and 15 characters"
        error_messages.append(error_message1)
        user_name_is_valid = False

    # checking if username only conatins alpha chars
    if username.isalpha() == False:
        error_message2 = "\t-usernames must only contain alpha characters"
        error_messages.append(error_message2)
        user_name_is_valid = False

    # if the username did not meet the qulifications, than the qulifications that were not met are printed to the user  
    if user_name_is_valid == False:
        print("That username does not meet the requirements:")
        for error in error_messages:
            print(error) 
    return user_name_is_valid

# function that takes the password that the user inputs and passes it through checks to enure that the password is valid  
def is_password_valid(password):
    error_messages = []
    password_is_valid = True
    # checking lenth of password
    if len(password) < 8:
        error_message1 = "\t-passwords must contain at least 8 characters"
        error_messages.append(error_message1)
        password_is_valid = False

    # checking if password has at least one uppercase character
    if password.islower() == True:
        error_message2 = "\t-passwords must contain at least one uppercase letter"
        error_messages.append(error_message2)
        password_is_valid = False

    # checking if password has at least one lowercase character
    if password.isupper() == True:
        error_message3 = "\t-passwords must contain at least one lowercase letter"
        error_messages.append(error_message3)
        password_is_valid = False

    # checking if password has at least one number
    if password.isalpha() == True:
        error_message4 = "\t-passwords must contain at least one numerical digit"
        error_messages.append(error_message4)
        password_is_valid = False

    # checking to make sure password has at least two alpha characters
    if password.isdigit() == True:
        error_message5 = "\t-passwords must contain at least two alpha character"
        error_messages.append(error_message5)
        password_is_valid = False

    # checking to make sure password has no extra spaces
    has_spaces = False
    for char in password:
        if char == ' ':
            has_spaces = True
            break
        
    if has_spaces == True:
        error_message6 = "\t-passwords cannot contain spaces"
        error_messages.append(error_message6)
        password_is_valid = False

    # checking to make sure password only has nums and alpha characters(not including extra spaces)      
    is_all_num_and_alpha = True

    if has_spaces == True:
        password_string = []
        for char in password:
            password_string.append(char)

        for char in password_string:
            if char == ' ':
                password_string.remove(char)

        for char in password_string:
            if char.isalnum() == False:
                is_all_num_and_alpha = False
                break

        if is_all_num_and_alpha == False:
            error_message7 = "\t-passwords cannot contain non-alphanumeric characters"
            error_messages.append(error_message7)
            password_is_valid = False

    # if there are no spaces in the password than a simple check for non-alphanumeric characters is done
    if has_spaces == False:
        if password.isalnum() == False:
            error_message7 = "\t-passwords cannot contain non-alphanumeric characters"
            error_messages.append(error_message7)
            password_is_valid = False
        
    # if the password did not meet the qulifications than the qulifications that were not met are printed to the user       
    if password_is_valid == False:
        print("That password does not meet the requirements:")
        for error in error_messages:
            print(error)
    return password_is_valid

# fuction that "signs" the user into the program        
def verify_user_sign_in(username_password_dict):
    verify_sign_in = input("-Username:\t")
    if verify_sign_in in username_password_dict:
        try_again = True
        while try_again == True:
            verify_password = input("-Password:\t")
            if username_password_dict[verify_sign_in] == verify_password:
                try_again = False
                print("\t-Sign in verified!")
                
            else:
                attempt_again = input("-Invalid password. Try again? (y/n)\t")
                if attempt_again == 'y':
                    try_again = True
                else:
                    print("\t-Your sign in has NOT been verified, now returning to the main menu.")
                    break
    else:
        print("\t-That user is not registered.")

# function that allows the user to update the username of an existing account 
def update_username(username_password_dict):
    verify_sign_in = input("-Username:\t")
    if verify_sign_in in username_password_dict:
        try_again = True
        while try_again == True:
            verify_password = input("-Password:\t")
            if username_password_dict[verify_sign_in] == verify_password:

                try_again = False
                print("-What do you want to change the username to?")
                is_new_user_valid = False
                while is_new_user_valid == False:
                    new_user = input("-New username:\t")
                    is_new_user_valid = is_username_valid(new_user)
                if is_new_user_valid == True:
                    username_password_dict.pop(verify_sign_in)
                    username_password_dict[new_user] = verify_password               
                    print("\t-Username successfully updated!")
                else:
                    print("-Try again")   
            else:
                attempt_again = input("-Invalid password. Try again? (y/n)\t")
                if attempt_again == 'y':
                    try_again = True
                else:
                    break
    else:
        print("\t-That user is not registered.")

# function that allows the user to update the password of an existing account 
def update_password(username_password_dict):
    verify_sign_in = input("Username:\t")
    if verify_sign_in in username_password_dict:
        try_again = True
        while try_again == True:
            verify_password = input("Password:\t")
            if username_password_dict[verify_sign_in] == verify_password:
                try_again = False
                print("-What do you want to change the password to?")

                is_new_pass_valid = False
                while is_new_pass_valid == False:
                    new_pass = input("-New password:\t")
                    is_new_pass_valid = is_password_valid(new_pass)
                if is_new_pass_valid == True:
                    username_password_dict[verify_sign_in] = new_pass
                    print("\t-Password successfully updated!")
                else:
                    print("-Try again")               
            else:
                attempt_again = input("-Invalid password. Try again? (y/n)\t")
                if attempt_again == 'y':
                    try_again = True
                else:
                    break
    else:
        print("\t-That user is not registered.")

# function that allows the user to delete an exisiting account    
def delete_account(username_password_dict):
    user_name = input("-Username:\t")
    if user_name in username_password_dict:
        try_again = True
        while try_again == True:
            password = input("-Password:\t")
            if username_password_dict[user_name] == password:
                try_again = False
                delete_account = input("-Are you sure that you want to delete this account? (y/n)\t")
                if delete_account == 'y':
                    username_password_dict.pop(user_name)
                    print("\t-Aw, that account has been deleted.")
                else:
                    print("\t-Operation has been abandoned")
                    break   
            else:
                attempt_again = input("-Thats an invalid password. Would you like to try again? (y/n)\t")
                if attempt_again == 'n':
                    print("\t-Operation has been abandoned")
                    try_again = False             
    else:
        print("\t-Yikes, that user is not registered in our system.")

# function that closes out of the program while also creating a "login.txt" file that contains all of the accounts usernames and passwords
def exiting_system(username_password_dict):
    username_password_list = []
    for username in username_password_dict:
        password = username_password_dict[username]
        username_password = username + " " + password + "\n"
        username_password_list.append(username_password)
    file = open("logins.txt", "w")
    file.writelines(username_password_list)
    file.close()
    print("\t-Exting system...\n"
          "\tYour username and password file has been created and is now ready to be view")

# creates the username_password_dict from the get_users_from_file function
username_password_dict = get_users_from_file()
print()
# main loop of the program
processing = 1
while processing == 1:
    option = main_menu()
    # if option 1 is called, the register_new_user function is called
    if option == 1:
        print()
        register_new_user(username_password_dict)
        print()

    # if option 2 is called, the verify_user_sign_in function is called    
    elif option == 2:
        print()
        verify_user_sign_in(username_password_dict)
        print()

    # if option 3 is called, the update_username function is called   
    elif option == 3:
        print()
        update_username(username_password_dict)
        print()

    # if option 4 is called, the update_password function is called            
    elif option == 4:
        print()
        update_password(username_password_dict)
        print()

    # if option 5 is called, the delete_account function is called        
    elif option == 5:
        print()
        delete_account(username_password_dict)
        print()

    # if option 6 is called, the exiting_system function is called and the loop is terminated      
    elif option == 6:
        print()
        exiting_system(username_password_dict)
        break

