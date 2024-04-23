class Pawsconnect:
        
    def parse_users(self,filename):
        users = {}
        with open(filename, 'r') as file:
            for line in file:
                parts = line.strip().split(';')
                username, display_name, state, friends_list = parts
                friends = friends_list.strip('[]').split(',') if len(friends_list.strip('[]')) > 0 else []
                users[username] = {
                    'display_name': display_name,
                    'state': state,
                    'friends': friends
                }
        return users

    def parse_posts(self,filename):
        posts = {}
        with open(filename, 'r') as file:
            for line in file:
                parts = line.strip().split(';')
                post_id, user_id, visibility = parts
                posts[post_id] = {
                    'user_id': user_id,
                    'visibility': visibility
                }
        return posts

    def can_view_post(self,post_id, username, users, posts):
        post = posts.get(post_id)
        if not post:
            return "Access Denied"
        if post['visibility'] == 'public':
            return "Access Permitted"
        if post['visibility'] == 'friend' and username in users[post['user_id']]['friends']:
            return "Access Permitted"
        return "Access Denied"

    def get_accessible_posts(self,username, users, posts):
        accessible_posts = []
        user_friends = users[username]['friends'] if username in users else []
        for post_id, post in posts.items():
            if post['user_id'] == username:
                continue
            if post['visibility'] == 'public' or (post['visibility'] == 'friend' and username in user_friends):
                accessible_posts.append(post_id)
        return accessible_posts

    def find_users_by_state(self,state, users):
        return [details['display_name'] for username, details in users.items() if details['state'] == state]

def main():
    pc=Pawsconnect()
    users = {}
    posts = {}
    while True:
        print("\nMenu")
        print("1. Load input data")
        print("2. Check visibility")
        print("3. Retrieve posts")
        print("4. Search users by location")
        print("5. Exit")
        choice = input("Choose an option: ")

        if choice == '1':
            user_file = input("Enter the path to the user info file: ")
            post_file = input("Enter the path to the post info file: ")
            users = pc.parse_users(user_file)
            posts = pc.parse_posts(post_file)
            print("Data loaded successfully.")

        elif choice == '2':
            post_id = input("Enter post ID: ")
            username = input("Enter username: ")
            result = pc.can_view_post(post_id, username, users, posts)
            print(result)

        elif choice == '3':
            username = input("Enter username: ")
            accessible_posts = pc.get_accessible_posts(username, users, posts)
            print(", ".join(accessible_posts))

        elif choice == '4':
            state = input("Enter state location: ")
            matching_users = pc.find_users_by_state(state, users)
            print(", ".join(matching_users))

        elif choice == '5':
            print("Exiting program.")
            break

        else:
            print("Invalid option, please try again.")
if __name__ == "__main__":
    main()
