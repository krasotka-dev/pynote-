import json, yaml
import os
import argparse

def main():
    parser = argparse.ArgumentParser(description='Jupyter notebook runner')

    parser.add_argument('-u', '--username',  help='Username for jupyter notebook')
    parser.add_argument('-p', '--password',  help='Password for jupyter notebook')

    args = parser.parse_args()

    data = {'NotebookApp': {'password': str(args.password) }}

    # with open('/root/.jupyter/jupyter_notebook_config.json', 'w') as file:
    #     json.dump(data, file, indent=2)

    ## Create Folder for user
    username = args.username.lower()
    os.mkdir(f'{username}')
    os.chdir(f'{username}')

    ## config the url for users
    os.system(f'sed  "s|USERNAME|{username}|g" /root/.jupyter/jupyter_notebook_config.py -i')
    ## Run the application
    os.system(f"jupyter notebook --ip=0.0.0.0 --port=8888 --no-browser --allow-root --NotebookApp.token={args.password}")

if __name__ == '__main__':
    main()
