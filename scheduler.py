import glob
import os
import shutil
import time
import schedule


def session_cleanup():
    session_path = ".sessions"
    all_sessions = glob.glob(session_path + "/**/**/**/**/**/**/**/**/**/**/**/**/**/**/**/*.dat")

    for session in all_sessions:
        print(session)
        modified_time = os.stat(session).st_mtime
        current_time = time.time()

        # Time in minutes since last modification of file
        expiry_time = (current_time - modified_time) / 60
        if expiry_time > 30:
            current_session_path = ".sessions\\" + session.split("\\", 2)[1]
            print(current_session_path)
            shutil.rmtree(current_session_path)
        else:
            current_session_path = ".sessions\\" + session.split("\\", 2)[1]
            print(current_session_path)

        print(expiry_time)


schedule.every(15).minutes.do(session_cleanup)

while True:
    schedule.run_pending()
    time.sleep(900)
