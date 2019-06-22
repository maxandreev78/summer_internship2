package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.ProjectType;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String project_list(Model model, @AuthenticationPrincipal User user,
                               @RequestParam(required = false) ProjectType type) {
        projectService.findByType(type, model);
        model.addAttribute("user_now", user);

        return "project_list";
    }

    @PostMapping
    public String create_project(@RequestParam String name, @RequestParam String description,
                                 @AuthenticationPrincipal User user) {
        projectService.create(name, description, user);

        return "redirect:/project";
    }

    @GetMapping("/{project}")
    public String project_info(Model model, @AuthenticationPrincipal User user,
                               @PathVariable Project project) {
        projectService.projectInfo(project, model);
        model.addAttribute("user_now", user);

        return "project_info";
    }

    @PostMapping("/{project}/edit")
    public String project_edit(@PathVariable Project project, @RequestParam String name,
                               @RequestParam String description, @AuthenticationPrincipal User user) {
        projectService.update(project, name, description, user);
        return "redirect:/project/" + project.getId();
    }

    @PostMapping("/{project}/add")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String project_add_user(@PathVariable Project project, @RequestParam String username) {
        projectService.addParticipant(project, username);

        return "redirect:/project/{project}";
    }

    @PostMapping("/{project}/comment")
    public String add_comment(@PathVariable Project project, @RequestParam String text,
                              @AuthenticationPrincipal User user) {
        projectService.addComment(project, text, user);

        return "redirect:/project/{project}";
    }

    @PostMapping("/{project}/supervisor")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String add_supervisor(@PathVariable Project project, @RequestParam String username) {
        projectService.addSupervisor(project, username);

        return "redirect:/project/{project}";
    }

    @PostMapping("/{project}/type")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String change_type(@PathVariable Project project, @RequestParam String type) {
        projectService.changeType(project, type);

        return "redirect:/project/{project}";
    }

    @PostMapping("/{project}/delete")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String delete_project(@PathVariable Project project) {
        projectService.delete(project);

        return "redirect:/project";
    }
}
