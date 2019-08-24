FROM mcr.microsoft.com/dotnet/core/sdk:2.2

ENV SHELL /bin/bash

# Configure apt
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt-get -y install --no-install-recommends apt-utils 2>&1

# Install git, process tools, lsb-release (common in install instructions for CLIs)
RUN apt-get -y install git procps lsb-release

# Install Docker CE CL
RUN apt-get install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common lsb-release \
    && curl -fsSL https://download.docker.com/linux/$(lsb_release -is | tr '[:upper:]' '[:lower:]')/gpg | (OUT=$(apt-key add - 2>&1) || echo $OUT) \
    && add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/$(lsb_release -is | tr '[:upper:]' '[:lower:]') $(lsb_release -cs) stable" \
    && apt-get update \
    && apt-get install -y docker-ce-cli

ENV CLOUD_SDK_VERSION 237.0.0

RUN export CLOUD_SDK_REPO="cloud-sdk-$(lsb_release -c -s)" \
    && echo "deb https://packages.cloud.google.com/apt $CLOUD_SDK_REPO main" > /etc/apt/sources.list.d/google-cloud-sdk.list \
    && curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add - \
    && apt-get update \
    && apt-get install -y google-cloud-sdk=${CLOUD_SDK_VERSION}-0 \
        kubectl \
    && gcloud --quiet config set core/disable_usage_reporting true \
    && gcloud --quiet config set component_manager/disable_update_check true \
    && gcloud --quiet config set metrics/environment github_docker_image 

# Install Skaffold
RUN curl -Lo skaffold https://storage.googleapis.com/skaffold/releases/latest/skaffold-linux-amd64 \
    && chmod +x skaffold \
    && mv skaffold /usr/local/bin

# Copy localhost's ~/.kube/config file into the container and swap out localhost
# for host.docker.internal whenever a new shell starts to keep them in sync.
RUN echo '\n\
if [ "$SYNC_LOCALHOST_KUBECONFIG" == "true" ]; then\n\
    mkdir -p $HOME/.kube\n\
    cp -r $HOME/.kube-localhost/* $HOME/.kube\n\
    sed -i -e "s/localhost/host.docker.internal/g" $HOME/.kube/config\n\
\n\
fi' \
>> $HOME/.bashrc

# Copy gcloud config from the host
RUN echo '\n\
if [ "$SYNC_LOCALHOST_GCLOUD" == "true" ]; then\n\
    mkdir -p $HOME/.config/gcloud\n\
    cp -r $HOME/.gcloud-localhost/* $HOME/.config/gcloud\n\
\n\
fi' \
>> $HOME/.bashrc

#COPY sync.sh /root/sync.sh
#RUN touch /root/.bashrc && /root/sync.sh >> $HOME/.bashrc
RUN echo '\n\
if [ "$SYNC_LOCALHOST_SSH" == "true" ]; then\n\
    mkdir -p ~/.ssh\n\
    cp -r ~/.ssh-localhost/* ~/.ssh\n\
    chmod 700 ~/.ssh \n\
    chmod 600 ~/.ssh/*\n\
fi' \
>> $HOME/.bashrc

# Clean up
RUN apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*
ENV DEBIAN_FRONTEND=dialog

# Set the default shell to bash rather than sh
ENV SHELL /bin/bash