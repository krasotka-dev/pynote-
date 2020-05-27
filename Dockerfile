# Pulling default image from DockerHub
FROM fsadykov/centos_python
MAINTAINER Farkhod Sadykov

## Install Jupyter Notebook
RUN python3.6 -m pip install jupyter
RUN python3.6 -m pip install pyyaml
RUN mkdir /root/.jupyter/ -p

## Install git on container
RUN yum install -y git

## Expose the port
EXPOSE 8888

## Chance logo jupyter notebook
COPY fuchicorp-logo.png /usr/lib/python3.6/site-packages/notebook/static/base/images/logo.png
COPY jupyter-runner.py /
COPY jupyter_notebook_config.py /root/.jupyter
RUN chmod +x /jupyter-runner.py

## Config the Git
RUN git config --global user.email "fuchicorp-student@fuchicorp.com" && git config --global user.name "FuchiCorp Student"
RUN git config --global credential.helper cache

ENTRYPOINT ["python3.6",  "/jupyter-runner.py"]
