#!/bin/sh -e
#? -e break the script if error
NODE_NAME=$(cat /etc/nodename)
echo "node_meta{node_id=\"$NODE_ID\", container_label_com_docker_swarm_node_id=\"$NODE_ID\", node_name=\"$NODE_NAME\"} 1" >/home/node-meta.prom
set -- /bin/node_exporter "$@"
exec "$@"
