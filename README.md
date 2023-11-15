# Task Manager App ✍️

## Overview

Task Manager App is a simple and intuitive task management application built with Kotlin. It allows users to create, manage, and organize their tasks efficiently.

## Features

- **Create Tasks:** Easily add new tasks with a title, description, and due date.
  <br />
- **Task List:** View all your tasks in a clean and organized list.
  <br />
- **Task Details:** Click on a task to view its details, including due date and description.

## Screenshots

<img src="https://private-user-images.githubusercontent.com/81030843/283212756-e839130a-1b66-44ff-897f-92f13d7496ff.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDAwNzMxNzgsIm5iZiI6MTcwMDA3Mjg3OCwicGF0aCI6Ii84MTAzMDg0My8yODMyMTI3NTYtZTgzOTEzMGEtMWI2Ni00NGZmLTg5N2YtOTJmMTNkNzQ5NmZmLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFJV05KWUFYNENTVkVINTNBJTJGMjAyMzExMTUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjMxMTE1VDE4Mjc1OFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWM1ZjI1N2Y5MWZlYTUxMGM0YTRlOTYwNjI1YWYwYThlZTU3MjdmOTk2NDQ0ZmRkMzJlNDRlZDUyMTM0OTc4Y2EmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.EH2IRXVCNyqfenLPpXFE4J31y05IA_mSNeKNqodutTE" style="width: 300px" />
<img src="https://private-user-images.githubusercontent.com/81030843/283212763-bb820f7b-968c-44ee-83bb-90ca457706ef.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDAwNzMxNzgsIm5iZiI6MTcwMDA3Mjg3OCwicGF0aCI6Ii84MTAzMDg0My8yODMyMTI3NjMtYmI4MjBmN2ItOTY4Yy00NGVlLTgzYmItOTBjYTQ1NzcwNmVmLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFJV05KWUFYNENTVkVINTNBJTJGMjAyMzExMTUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjMxMTE1VDE4Mjc1OFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTc2NGMxMjJkNzAzNjU3Yzk2NGYwNDYzZDRmYTI5ZDU4MGUyN2YyNWRlNTE2NjM5Mjk0MTYxODg4YmY4ZmQzOWEmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.w0yHaRey6vr6TZf1O9zwPnwYbHuCVsM0PUHpxKiUi9c" style="width: 300px" />
<img src="https://private-user-images.githubusercontent.com/81030843/283212768-a3d54e24-19e6-48e2-8e8f-c599cc70cd3b.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDAwNzMxNzgsIm5iZiI6MTcwMDA3Mjg3OCwicGF0aCI6Ii84MTAzMDg0My8yODMyMTI3NjgtYTNkNTRlMjQtMTllNi00OGUyLThlOGYtYzU5OWNjNzBjZDNiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFJV05KWUFYNENTVkVINTNBJTJGMjAyMzExMTUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjMxMTE1VDE4Mjc1OFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWJhNDMzNzlmNzU3NzY1YjAwMGI2YWRhZmNmNGRkMDk2ZTY2ODYzYWM1MGMyYmM1MTJlNDgwMmVkMzA0NmVkMWMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.I1MBJmICaUgEmSza4Ppx04_o-B7697dqaIDMjaYt3xU" style="width: 300px" />
<img src="https://private-user-images.githubusercontent.com/81030843/283212774-c7122376-46c9-4063-9dac-05923bc9d657.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDAwNzMxNzgsIm5iZiI6MTcwMDA3Mjg3OCwicGF0aCI6Ii84MTAzMDg0My8yODMyMTI3NzQtYzcxMjIzNzYtNDZjOS00MDYzLTlkYWMtMDU5MjNiYzlkNjU3LmdpZj9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFJV05KWUFYNENTVkVINTNBJTJGMjAyMzExMTUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjMxMTE1VDE4Mjc1OFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWUyMGQ0ZjAzMWRkMjBmNTczYzA2MGZhNzkwMDAzMDA0MWVlNDQxMzg5ZDdhNzQ5NjYxYTFkYzllODk2Y2NlNzkmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.Er07ldQdYYtUFCChFM25dcBhmW8atZco1QN5_fXNMvo" style="width: 300px" />

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/GuilhermeIgnacio/TaskApp
   ```

2. Open the project in Android Studio.

3. Build and run the application on your Android device or emulator.

Or:

1. [Here](app/release/app-release.apk) Download the APK at app/release/app-release.apk

## Usage

1. Launch the app on your device.

2. Click on the "+" button to add a new task.

3. Fill in the task details, including title, description, and due date.

4. Save the task, and it will appear in the task list.

5. Tap on a task to view its details or mark it as complete.

6. Swipe from start to end of the screen to delete a task

## Technologies Used

- Kotlin
- Android Studio
- Room (for local storage)

## Contributing

We welcome contributions! If you find any bugs or have ideas for new features, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License

## Acknowledgments

- Thanks to [saket/swipe](https://github.com/GuilhermeIgnacio/TasksApp) library for contributing with swipe-to-delete.
