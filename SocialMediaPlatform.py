class SocialMediaPlatform:
    def __init__(self):
        self.users = {}
        self.posts = {}

    def load_data(self, user_file, post_file):
        self.load_users(user_file)
        self.load_posts(post_file)

    def load_users(self, filepath):
        with open(filepath, 'r') as file:
            for line in file:
                parts = line.strip().split(';')
                username = parts[0]
                display_name = parts[1]
                state = parts[2]
                friends_list = parts[3].strip('[]').split(',') if parts[3] != '[]' else []
                self.users[username] = {'display_name': display_name, 'state': state, 'friends': friends_list}

    def load_posts(self, filepath):
        with open(filepath, 'r') as file:
            for line in file:
                parts = line.strip().split(';')
                post_id = parts[0]
                user_id = parts[1]
                visibility = parts[2]
                self.posts[post_id] = {'user_id': user_id, 'visibility': visibility}

    def check_visibility(self, post_id, username):
        if post_id in self.posts:
            post = self.posts[post_id]
            if post['visibility'] == 'public':
                return "Access Permitted"
            elif post['visibility'] == 'friend':
                if username in self.users[post['user_id']]['friends']:
                    return "Access Permitted"
        return "Access Denied"

    def retrieve_posts(self, username):
        visible_posts = []
        for post_id, post_info in self.posts.items():
        	# Include public posts
            if post_info['visibility'] == 'public':
                visible_posts.append(post_id)
            # Include posts visible to friends, including the user's own if they are the author
            elif post_info['visibility'] == 'friend':
                if username in self.users[post_info['user_id']]['friends'] or post_info['user_id'] == username:
                    visible_posts.append(post_id)
        return visible_posts

    def search_users_by_location(self, state):
        return [user_info['display_name'] for username, user_info in self.users.items() if user_info['state'] == state]

def main():
    platform = SocialMediaPlatform()
    while True:
        print("\nMenu")
        print("1. Load input data")
        print("2. Check visibility")
        print("3. Retrieve posts")
        print("4. Search users by location")
        print("5. Exit")
        choice = input("Choose an option: ")

        if choice == '1':
            user_file = input("Enter path to the user information file: ")
            post_file = input("Enter path to the post information file: ")
            platform.load_data(user_file, post_file)
            print("Data loaded successfully.")
        elif choice == '2':
            post_id = input("Enter post ID: ")
            username = input("Enter username: ")
            result = platform.check_visibility(post_id, username)
            print(result)
        elif choice == '3':
            username = input("Enter username: ")
            posts = platform.retrieve_posts(username)
            print(", ".join(posts) if posts else "No posts available.")
        elif choice == '4':
            state = input("Enter state: ")
            display_names = platform.search_users_by_location(state)
            print(", ".join(display_names) if display_names else "No users found.")
        elif choice == '5':
            print("Exiting program.")
            break
        else:
            print("Invalid option, please try again.")

if __name__ == "__main__":
    main()

