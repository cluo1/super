package com.luo.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name="super")
public class LuoPlugin extends AbstractMojo {
    @Parameter
    private String name;

    @Parameter
    private String decribe;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(String.format("super name is %s,decribe is %s",name,decribe));
    }
}
